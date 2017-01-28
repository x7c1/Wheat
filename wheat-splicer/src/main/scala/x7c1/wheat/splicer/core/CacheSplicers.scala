package x7c1.wheat.splicer.core

import sbt.Def.Classpath
import sbt.{Attributed, File, ModuleID, ProcessLogger, globFilter, singleFileFinder}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.lib.{ModuleIdFactory, Reader}
import x7c1.wheat.splicer.maven.{ArchiveCache, ArchiveCacheFinder, ArchiveCacheTraverser}


class CacheSplicers private(sdk: AndroidSdk, splicers: Seq[CacheSplicer]) {

  def expandAll: Reader[ProcessLogger, Unit] = {
    val readers = splicers.map(_.setupJars) ++ splicers.map(_.setupSources)
    readers.uniteAll
  }

  def cleanAll: Reader[ProcessLogger, Unit] = {
    val readers = splicers.map(_.clean)
    readers.uniteAll
  }

  def classpath: Classpath = {
    val empty: Classpath = Attributed blankSeq Seq()
    splicers.foldLeft(empty)(_ ++ _.loadClasspath) ++ (sdk.platforms * "*.jar").classpath
  }

  def sourceDirectories: Seq[File] = {
    val empty: Seq[File] = Seq()
    splicers.foldLeft(empty)(_ ++ _.sourceDirectories)
  }
}

object CacheSplicers {

  class Factory(unmanagedDirectory: File, sdk: AndroidSdk) {

    def create(dependencies: Seq[String]): CacheSplicers = {
      val create = new CacheSplicer.Factory(
        cacheDirectory = cacheDirectory,
        unmanagedDirectory = unmanagedDirectory.getCanonicalFile,
        sdk = sdk
      )
      val caches = dependencies map ModuleIdFactory.create map toCache
      new CacheSplicers(
        sdk = sdk,
        splicers = filter(caches) map create.fromCache
      )
    }

    private val cacheDirectory = sdk.`android-m2repository`

    private val finder = new ArchiveCacheFinder(cacheDirectory)

    private def toCache(moduleID: ModuleID): ArchiveCache = {
      finder fromModule moduleID match {
        case Left(error) => throw new IllegalArgumentException(error.message)
        case Right(cache) => cache
      }
    }

    private def filter(caches: Seq[ArchiveCache]): Seq[ArchiveCache] = {
      val traverser = ArchiveCacheTraverser(cacheDirectory)
      traverser resolveAll caches match {
        case Left(error) => throw new IllegalArgumentException(error.message)
        case Right(xs) => xs
      }
    }

  }

}
