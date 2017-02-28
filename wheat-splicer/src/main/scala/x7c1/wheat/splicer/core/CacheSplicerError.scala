package x7c1.wheat.splicer.core

import sbt.File
import x7c1.chaff.process.{HasLogMessage, LogMessage}
import x7c1.chaff.process.LogMessage.Error
import x7c1.wheat.splicer.lib.HasMessage


sealed trait CacheSplicerError {
  def message: String
}

object CacheSplicerError {

  case class NotFound(file: File) extends CacheSplicerError {
    override def message: String = s"file not found: $file"
  }

  case class Propagated[A: HasMessage](cause: A) extends CacheSplicerError {
    override def message: String = implicitly[HasMessage[A]] messageOf cause
  }

  case class Unexpected(cause: Exception) extends CacheSplicerError {
    override def message: String = cause.getMessage
  }

  implicit object hasLogMessage extends HasLogMessage[CacheSplicerError] {
    override def messageOf(x: CacheSplicerError): LogMessage = Error(x.message)
  }

}
