package x7c1.wheat.splicer.core

import org.slf4j.Logger
import sbt.ProcessLogger


case class LoggerWrapper(logger: Logger) extends ProcessLogger {

  override def info(s: => String): Unit = {
    logger.info(s)
  }

  override def error(s: => String): Unit = {
    logger.error(s)
  }

  override def buffer[T](f: => T): T = f
}
