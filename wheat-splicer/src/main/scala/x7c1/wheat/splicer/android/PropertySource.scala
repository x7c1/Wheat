package x7c1.wheat.splicer.android

import java.net.URL

import sbt.IO.utf8
import sbt.{File, IO, Using}


sealed trait PropertySource {

  def name: String

  def lines: List[String]
}

case class PropertyFile(content: File) extends PropertySource {

  override def name: String = content.getName

  override def lines = IO.readLines(content)

}

case class PropertyResource(name: String) extends PropertySource {

  private def url: URL = getClass.getResource(name)

  override def lines = Using.urlReader(utf8)(url)(IO.readLines)
}
