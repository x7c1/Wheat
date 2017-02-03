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
      (frameworkPackages :+ getClass.getPackage.getName) foreach add
    }

  val appenderFactories: Seq[Appender.Factory]

  lazy val appenders: Seq[core.Appender[ILoggingEvent]] = {
    appenderFactories.map(_ (context))
  }

  def level: Level

  def frameworkPackages: Seq[String] = Seq()

  def apply[X](klass: Class[X]): slf4j.Logger = {
    slf4j.LoggerFactory.getLogger(klass).asInstanceOf[classic.Logger].tap(
      appenders foreach _.addAppender,
      _ setLevel level,
      _ setAdditive false
    )
  }

}

object Appender {

  type Factory = LoggerContext => core.Appender[ILoggingEvent]

  type From[X] = X => Factory

  def from[X: From](x: X): Factory = {
    implicitly[From[X]] apply x
  }
}
