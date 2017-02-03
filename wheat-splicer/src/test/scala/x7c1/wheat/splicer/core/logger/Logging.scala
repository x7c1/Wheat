package x7c1.wheat.splicer.core.logger

import ch.qos.logback.classic.Level
import sbt.ProcessLogger


trait Logging {
  lazy val logger: ProcessLogger = LoggerWrapper(Logging(getClass))
}

object Logging extends LoggerFactory {

  override lazy val level = Level.DEBUG

  override def frameworkPackages = Seq(
    classOf[LoggerWrapper].getPackage.getName
  )

  override lazy val appenderFactories = Seq(
    Appender from RollingFileSetting(
      encoderPattern = """%d{yyyy-MM-dd'T'HH:mm:ss'Z'} [%thread] %level %logger{0} - %msg \(%file:%line\)%n""",
      fileName = "logs/app.log",
      fileNamePattern = "logs/app.%d{yyyy-MM-dd}.log",
      maxHistory = 7
    )
  )

}
