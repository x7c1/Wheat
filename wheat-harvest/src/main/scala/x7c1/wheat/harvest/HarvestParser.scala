package x7c1.wheat.harvest

import sbt.PathFinder
import sbt.complete.Parser
import x7c1.chaff.parser.reductive.ReductiveParser


object HarvestParser {

  import sbt.complete.DefaultParsers._

  lazy val identifier: Parser[String] = {
    val alphabet = token('a' to 'z')
    val numbers = token('0' to '9')
    alphabet.+.string ~ (numbers | alphabet).*.string map {
      case (a, b) => a + b
    }
  }

  def camelizeTail(string: String): Either[HarvestParserError, String] = {
    val parser = (identifier ~ (token('_') ~> identifier).*) map {
      case (head, tail) => head + tail.map(_.capitalize).mkString
    }
    parse(string, parser).left.map(HarvestParserError)
  }

  def selectFrom(finder: PathFinder): Parser[Seq[String]] = {
    val names = finder.get.map(_.getName) filterNot (_ startsWith "_")
    ReductiveParser from names
  }

}

case class HarvestParserError(message: String)
