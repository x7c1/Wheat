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

  implicit class RichEitherReader[A <: HasProcessLogger, L: HasLogMessage, R: HasLogMessage](
    reader: Reader[A, Either[L, R]]) {

    def asLoggerApplied: Reader[A, Unit] = {
      reader map {
        case Right(right) => implicitly[HasLogMessage[R]] messageOf right
        case Left(left) => implicitly[HasLogMessage[L]] messageOf left
      } flatMap (_.toReader)
    }
  }

  def LogReader[A](f: ProcessLogger => A): Reader[HasProcessLogger, A] =
    Reader { context =>
      f(context.logger)
    }
}
