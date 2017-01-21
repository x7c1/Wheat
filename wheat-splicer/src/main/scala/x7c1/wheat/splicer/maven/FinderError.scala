package x7c1.wheat.splicer.maven

import x7c1.wheat.splicer.lib.HasMessage

case class FinderError(message: String)

object FinderError {

  implicit object hasMessage extends HasMessage[FinderError] {
    override def messageOf(x: FinderError): String = {
      s"${x.getClass.getSimpleName}: ${x.message}"
    }
  }

}
