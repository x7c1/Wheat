package x7c1.wheat.splicer.lib

import sbt.ProcessLogger

case class Reader[X, A](run: X => A) {
  def map[B](f: A => B): Reader[X, B] = {
    new Reader[X, B](x => f(run(x)))
  }

  def flatMap[B](f: A => Reader[X, B]): Reader[X, B] = {
    new Reader[X, B](x => f(run(x)) run x)
  }
}

object Reader {

  implicit class RichUnitReader[A](reader: Reader[A, Unit]) {
    def append(next: Reader[A, Unit]): Reader[A, Unit] = {
      reader.flatMap(_ => next)
    }
  }

  implicit class RichUnitReaders[A](readers: Seq[Reader[A, Unit]]) {
    def uniteAll: Reader[A, Unit] = {
      val nop = Reader[A, Unit](_ => ())
      readers.foldLeft(nop)(_ append _)
    }
  }

  implicit class RichEitherReader[L: HasLogMessage, R: HasLogMessage](
    reader: Reader[HasProcessLogger, Either[L, R]]) {

    def asLoggerApplied: Reader[HasProcessLogger, Unit] = {
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
