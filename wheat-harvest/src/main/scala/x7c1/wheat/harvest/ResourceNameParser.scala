package x7c1.wheat.harvest

import sbt.complete.Parser


object ResourceNameParser {

  import sbt.complete.DefaultParsers._

  def readPrefix(name: String): Either[HarvestParserError, ResourcePrefix] = {
    parse(name, parserToPrefix).left.map(HarvestParserError).joinRight
  }

  def identifier = HarvestParser.identifier

  def parserToPrefix: Parser[Either[HarvestParserError, ResourcePrefix]] = {
    val wordsParser = identifier ~ (token('_') ~> identifier).* map {
      case (x, xs) => Words(x +: xs)
    }
    any.*.string <~ token(".xml") map { raw =>
      val p = "_".? ~ (wordsParser <~ token("__")).? ~ wordsParser map {
        case ((underscore, parent), words) =>
          val parentName = parent map (_.camelize)
          val privatePrefix = underscore getOrElse ""
          ResourcePrefix(
            raw = raw,
            ofClass = privatePrefix + (parentName getOrElse "") + words.camelize,
            ofKey = raw + "__",
            parentClassName = parentName map (privatePrefix + _)
          )
      }
      parse(raw, p).left map HarvestParserError
    }
  }

  private case class Words(values: Seq[String]) {
    def camelize = values.map(_.capitalize).mkString
  }

}
