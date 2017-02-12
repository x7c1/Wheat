package x7c1.wheat.splicer.lib

import sbt.{Logger, ProcessLogger}

import scala.language.implicitConversions

trait HasProcessLogger {
  def logger: ProcessLogger
}

object HasProcessLogger {
  implicit def fromLogger(x: Logger): HasProcessLogger =
    new HasProcessLogger {
      override def logger = Logger.log2PLog(x)
    }

  implicit def fromProcessLogger(x: ProcessLogger): HasProcessLogger =
    new HasProcessLogger {
      override def logger = x
    }
}
