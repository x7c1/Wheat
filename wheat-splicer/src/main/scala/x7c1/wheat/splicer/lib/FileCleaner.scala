package x7c1.wheat.splicer.lib

import sbt.{File, ProcessLogger}


object FileCleaner {

  def remove(file: File): Unit = {
    sbt.Defaults.doClean(
      clean = Seq(file),
      preserve = Seq()
    )
  }

  object withLogging {
    def remove(file: File): Reader[ProcessLogger, Unit] = Reader { logger =>
      FileCleaner remove file
      logger info s"[done] removed: $file"
    }
  }

}
