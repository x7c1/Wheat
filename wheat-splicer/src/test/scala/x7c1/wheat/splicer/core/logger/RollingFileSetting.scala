package x7c1.wheat.splicer.core.logger

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core
import ch.qos.logback.core.FileAppender
import ch.qos.logback.core.rolling.{RollingFileAppender, TimeBasedRollingPolicy}
import ch.qos.logback.core.util.ContextUtil
import org.slf4j
import x7c1.wheat.splicer.core.logger.Tap.implicits.Provider

case class RollingFileSetting(
  encoderPattern: String,
  fileName: String,
  fileNamePattern: String,
  maxHistory: Int
)

object RollingFileSetting {

  implicit def createAppender: RollingFileSetting => core.Appender[ILoggingEvent] = {
    setting =>
      val context = slf4j.LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext] tap { x =>
        new ContextUtil(x).addFrameworkPackage(
          x.getFrameworkPackages,
          getClass.getPackage.getName
        )
      }
      val encoder = new PatternLayoutEncoder().tap(
        _ setPattern setting.encoderPattern,
        _ setContext context,
        _ start()
      )
      val policy = new TimeBasedRollingPolicy[ILoggingEvent]().tap(
        _ setFileNamePattern setting.fileNamePattern,
        _ setMaxHistory setting.maxHistory,
        _ setContext context,
        _ setParent new FileAppender[ILoggingEvent]().tap(
          _ setFile setting.fileName
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
