package x7c1.wheat.splicer.lib

import sbt.ProcessLogger

trait HasProcessLogger {
  def logger: ProcessLogger
}
}
