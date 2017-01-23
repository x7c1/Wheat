package x7c1.wheat.splicer.android

import sbt.complete.DefaultParsers.parse
import sbt.complete.Parser
import x7c1.wheat.splicer.lib.Extractor
import x7c1.wheat.splicer.lib.Extractor.==>

class LineLoader(
  source: PropertySource,
  property: String,
  target: Parser[String]) {

  def requireSingle(): String = {
    loadMultiple() match {
      case x +: Seq() => x
      case x +: xs =>
        val targets = xs mkString ", "
        throw new IllegalArgumentException(s"multiple $property found: $targets")
      case Seq() =>
        throw new IllegalArgumentException(s"$property not found: ${source.name}")
    }
  }

  def loadMultiple(): Seq[String] = {
    val pattern = toPattern
    source.lines collect { case pattern(line) => line }
  }

  private def toPattern: String ==> String = Extractor {
    line => parse(line, target).right.toOption
  }

}
