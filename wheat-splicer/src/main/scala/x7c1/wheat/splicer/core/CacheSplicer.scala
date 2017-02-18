package x7c1.wheat.splicer.core

import sbt.Def.Classpath
import sbt.Path.richFile
import sbt.{File, PathFinder, globFilter, singleFileFinder}
import x7c1.wheat.splicer.android.{AndroidSdk, RGenerator}
import x7c1.wheat.splicer.core.CacheSplicerError.{NotFound, Propagated}
import x7c1.wheat.splicer.lib.Extractor.==>
import x7c1.wheat.splicer.lib.LogMessage.{Error, Info}
import x7c1.wheat.splicer.lib.{ArchiveExtractor, Extractor, FileCleaner, HasLogMessage, HasProcessLogger, Reader}
import x7c1.wheat.splicer.maven.{AarCache, ArchiveCache, ArchiveCacheTraverser, JarCache}


sealed trait CacheSplicer {

  def setupJars: Reader[HasProcessLogger, Unit]

  def setupSources: Reader[HasProcessLogger, Unit]

  def clean: Reader[HasProcessLogger, Unit]

  def loadClasspath: Classpath

  def sourceDirectories: Seq[File]
}

object CacheSplicer {

  class Factory(cacheDirectory: File, unmanagedDirectory: File, sdk: AndroidSdk) {
    def fromCache(cache: ArchiveCache): CacheSplicer = {
      cache match {
        case aar: AarCache =>
          new AarCacheExpander(cacheDirectory, unmanagedDirectory, sdk, aar)
        case jar: JarCache =>
          new JarCacheLoader(cacheDirectory, jar)
        case unknown =>
          val name = unknown.getClass.getName
          throw new IllegalArgumentException(
            s"unknown archive type [$name] : ${cache.moduleId}")
      }
    }
  }

}

class AarCacheExpander(
  cacheDirectory: File,
  unmanagedDirectory: File,
  sdk: AndroidSdk,
  cache: AarCache) extends CacheSplicer {

  private val destination = {
    unmanagedDirectory / cache.moduleId.name
  }
  private val sourceDestination = {
    destination / "src-generated"
  }
  private val manifest = {
    destination / "AndroidManifest.xml"
  }

  override def loadClasspath = {
    val finders = Seq(
      PathFinder(destination / "classes.jar"),
      destination / "libs" * "*.jar"
    )
    finders.foldLeft(PathFinder.empty)(_ +++ _).classpath
  }

  override def sourceDirectories = {
    Seq(sourceDestination.getAbsoluteFile)
  }

  override def setupJars = {
    val setup = () => for {
      _ <- mkdirs(destination).right
    } yield {
      val extractor = ArchiveExtractor(destination)
      extractor unzip cache.file
    }
    implicit val toMessage = HasLogMessage[Int] {
      case 0 =>
        Info(s"[done] ${cache.moduleId} expanded -> $destination")
      case n =>
        Error(s"(code:$n) failed to extract ${cache.file}")
    }
    setup.asLoggerApplied
  }

  override def setupSources = {
    val setup = () => for {
      _ <- mkdirs(sourceDestination).right
      dirs <- resourceDirectories.right
    } yield {
      val generator = RGenerator(sdk, manifest, sourceDestination)
      generator generateFrom dirs
    }
    implicit val toMessage = HasLogMessage[Int] {
      case 0 => (sourceDestination ** "*.java").get match {
        case files if files.nonEmpty =>
          Info(files map (s"[done] generated -> " + _): _*)
        case none =>
          Info(s"[done] no output: ${cache.moduleId}")
      }
      case n =>
        Error(s"(code:$n) failed to generate R.java: ${cache.moduleId}")
    }
    setup.asLoggerApplied
  }

  private val notFound: Seq[File] ==> File = Extractor {
    _ find (!_.exists())
  }

  private def resourceDirectories: Either[CacheSplicerError, Seq[File]] = {
    val collect: Seq[ArchiveCache] => Seq[File] = _ collect {
      case aar: AarCache => aar
    } map {
      unmanagedDirectory / _.moduleId.name / "res"
    }
    val caches = {
      val traverser = ArchiveCacheTraverser(cacheDirectory)
      traverser.resolve(cache).left map (Propagated(_))
    }
    caches.right map collect match {
      case Right(notFound(res)) => Left(NotFound(res))
      case x => x
    }
  }

  private def mkdirs(file: File): Either[CacheSplicerError, Unit] =
    try {
      Right(file.mkdirs())
    } catch {
      case e: Exception =>
        Left(CacheSplicerError.Unexpected(e))
    }

  override def clean = {
    FileCleaner.withLogging remove destination
  }
}

class JarCacheLoader(
  cacheDirectory: File,
  cache: JarCache) extends CacheSplicer {

  override def setupJars = Reader { context =>
    context.logger info s"[done] skipped: ${cache.moduleId} jar found: ${cache.file}"
  }

  override def setupSources = Reader { context =>
    context.logger info s"[done] skipped: no source to generate: ${cache.file.name}"
  }

  override def loadClasspath: Classpath = {
    PathFinder(cache.file).classpath
  }

  override def sourceDirectories = Seq()

  override def clean = Reader { context =>
    val logger = context.logger
    logger info s"[done] skipped: no files to clean: ${cache.moduleId}"
  }
}
