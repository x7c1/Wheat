package x7c1.wheat.splicer.core.logger

import ch.qos.logback.classic
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.{Level, LoggerContext}
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.{Appender, FileAppender}
import ch.qos.logback.core.rolling.{RollingFileAppender, TimeBasedRollingPolicy}
import ch.qos.logback.core.util.ContextUtil
import org.slf4j.LoggerFactory
import sbt.ProcessLogger
import x7c1.wheat.splicer.core.logger.Tap.implicits.Provider


trait Logging {
  lazy val logger: ProcessLogger = LoggerWrapper(Factory createLogger getClass)
}

object Factory {

  def createLogger[A](klass: Class[A]): classic.Logger = {
    LoggerFactory.getLogger(klass).asInstanceOf[classic.Logger].tap(
      _ setLevel Level.DEBUG,
      _ addAppender appender,
      _ setAdditive false
    )
  }

  private lazy val appender: Appender[ILoggingEvent] = {
    val context = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext] tap { x =>
      new ContextUtil(x).addFrameworkPackage(
        x.getFrameworkPackages,
        getClass.getPackage.getName
      )
    }
    val encoder = new PatternLayoutEncoder().tap(
      _ setPattern """%d{yyyy-MM-dd'T'HH:mm:ss'Z'} [%thread] %level %logger{0} - %msg \(%file:%line\)%n""",
      _ setContext context,
      _ start()
    )
    val policy = new TimeBasedRollingPolicy[ILoggingEvent]().tap(
      _ setFileNamePattern """logs/app.%d{yyyy-MM-dd}.log""",
      _ setMaxHistory 7,
      _ setContext context,
      _ setParent new FileAppender[ILoggingEvent]().tap(
        _ setFile "logs/app.log"
      ),
      _ start()
    )
    new RollingFileAppender[ILoggingEvent]().tap(
      _ setEncoder encoder,
      _ setContext context,
      _ setRollingPolicy policy,
      _ start()
    )
  }

}
