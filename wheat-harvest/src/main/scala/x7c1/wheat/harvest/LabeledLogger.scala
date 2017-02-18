package x7c1.wheat.harvest

import sbt.{Logger, ProcessLogger}

class LabeledLogger private(
  prefix: String,
  logger: ProcessLogger) extends ProcessLogger {

  override def info(message: => String): Unit = {
    logger info s"${Console.BLUE}[$prefix]${Console.RESET} $message"
  }

  override def error(message: => String): Unit = {
    logger error s"${Console.RED}[$prefix]${Console.RESET} $message"
  }

  override def buffer[T](f: => T): T = f
}

object LabeledLogger {
  def apply(label: String, delegateTo: Logger): LabeledLogger = {
    new LabeledLogger(label, delegateTo)
  }
}
