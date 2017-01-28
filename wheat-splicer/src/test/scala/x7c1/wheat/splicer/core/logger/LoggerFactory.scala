package x7c1.wheat.splicer.core.logger

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.{Level, LoggerContext}
import ch.qos.logback.core.util.ContextUtil
import ch.qos.logback.{classic, core}
import org.slf4j
import x7c1.wheat.splicer.core.logger.Tap.implicits.Provider


trait LoggerFactory {

  val context: LoggerContext =
    slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext] tap { x =>
      val util = new ContextUtil(x)
      val add = util.addFrameworkPackage(x.getFrameworkPackages, _: String)
      Seq(getClass.getPackage.getName) foreach add
    }

  val appenderFactories: Seq[LoggerContext => core.Appender[ILoggingEvent]]

  lazy val appenders: Seq[core.Appender[ILoggingEvent]] = {
    appenderFactories.map(_ (context))
  }

  def level: Level

  def apply[X](klass: Class[X]): slf4j.Logger = {
    slf4j.LoggerFactory.getLogger(klass).asInstanceOf[classic.Logger].tap(
      appenders foreach _.addAppender,
      _ setLevel level,
      _ setAdditive false
    )
  }

}

object CoreAppender {

  type From[X] = X => LoggerContext => core.Appender[ILoggingEvent]

  def from[X: From](x: X): LoggerContext => core.Appender[ILoggingEvent] = {
    implicitly[From[X]] apply x
  }
}
