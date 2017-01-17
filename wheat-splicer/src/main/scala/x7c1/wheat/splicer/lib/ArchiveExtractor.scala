package x7c1.wheat.splicer.lib

import sbt.Process
import sbt.{File, Logger}

object ArchiveExtractor {
  def apply(logger: Logger, destination: File): ArchiveExtractor = {
    new ArchiveExtractor(logger, destination)
  }
}

class ArchiveExtractor private(logger: Logger, destination: File) {

  def unzip(archive: File): Int = {
    val builder = Process apply Seq(
      "unzip", "-o", "-d",
      destination.getAbsolutePath,
      archive.getAbsolutePath
    )
    builder !< logger
  }
}
