package x7c1.wheat.splicer.core.logger

import ch.qos.logback.classic
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core
import org.slf4j
import x7c1.wheat.splicer.core.logger.Tap.implicits.Provider


object Logger {
  def apply[A](x: A)(implicit create: A => slf4j.Logger): slf4j.Logger = {
    create(x)
  }
}

trait LoggerFactory[X] {

  def appender: core.Appender[ILoggingEvent]

  def level: Level

  implicit def createLogger[A <: X]: Class[A] => slf4j.Logger = {
    klass =>
      slf4j.LoggerFactory.getLogger(klass).asInstanceOf[classic.Logger].tap(
        _ setLevel level,
        _ addAppender appender,
        _ setAdditive false
      )
  }
}

object Appender {
  def from[A, EVENT](setting: A)(implicit convert: A => core.Appender[EVENT]): core.Appender[EVENT] = {
    convert(setting)
  }
}
