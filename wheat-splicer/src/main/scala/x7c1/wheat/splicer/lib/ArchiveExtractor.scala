package x7c1.wheat.splicer.lib

import sbt.File
import x7c1.chaff.process.ProcessRunner

object ArchiveExtractor {
  def apply(destination: File): ArchiveExtractor = {
    new ArchiveExtractor(destination)
  }
}

class ArchiveExtractor private(destination: File) {

  def unzip(archive: File): ProcessRunner = {
    ProcessRunner apply Seq(
      "unzip", "-o", "-d",
      destination.getAbsolutePath,
      archive.getAbsolutePath
    )
  }
}
