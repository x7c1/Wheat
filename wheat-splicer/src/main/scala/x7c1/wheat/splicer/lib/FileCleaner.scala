package x7c1.wheat.splicer.lib

import sbt.File
import x7c1.chaff.process.HasProcessLogger
import x7c1.chaff.reader.Reader


object FileCleaner {

  def remove(file: File): Unit = {
    sbt.Defaults.doClean(
      clean = Seq(file),
      preserve = Seq()
    )
  }

  object withLogging {

    def remove(file: File): Reader[HasProcessLogger, Unit] =
      Reader { context =>
        FileCleaner remove file
        context.logger info s"[done] removed: $file"
      }
  }
}
