package x7c1.wheat.splicer.core.logger

import ch.qos.logback.classic
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core
import org.slf4j
import x7c1.wheat.splicer.core.logger.Tap.implicits.Provider


trait LoggerFactory {

  val appenders: Seq[core.Appender[ILoggingEvent]]

  val level: Level

  def apply[X](klass: Class[X]): slf4j.Logger = {
    slf4j.LoggerFactory.getLogger(klass).asInstanceOf[classic.Logger].tap(
      appenders foreach _.addAppender,
      _ setLevel level,
      _ setAdditive false
    )
  }

}

object CoreAppender extends FactoryInferable[core.Appender[ILoggingEvent]]
