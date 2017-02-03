package x7c1.wheat.splicer.lib

import sbt.{File, Process, ProcessBuilder}

object ArchiveExtractor {
  def apply(destination: File): ArchiveExtractor = {
    new ArchiveExtractor(destination)
  }
}

class ArchiveExtractor private(destination: File) {

  def unzip(archive: File): ProcessBuilder = {
    Process apply Seq(
      "unzip", "-o", "-d",
      destination.getAbsolutePath,
      archive.getAbsolutePath
    )
  }
}
