package x7c1.wheat.splicer.android

import sbt.File
import sbt.complete.DefaultParsers._
import sbt.complete.Parser


object PropertyLoader {

  object sdkRoot extends LinedProperty[File](
    parser = _ ~> "=" ~> NotSpace,
    property = "sdk.dir"
  )

  object buildToolsVersion extends LinedProperty[String](
    parser = Space ~> _ ~> Space ~> quoted,
    property = "buildToolsVersion"
  )

  object compileSdkVersion extends LinedProperty[Int](
    parser = Space ~> _ ~> Space ~> Digit.+.string,
    property = "compileSdkVersion"
  )

  object dependencies extends LinedProperty[Seq[String]](
    parser = Space ~> _ ~> Space ~> quoted,
    property = "compile"
  )

  private val quoted = {
    val quoted1 = "'" ~> NotSpace <~ "'"
    val quoted2 = '"' ~> NotSpace <~ '"'
    quoted1 | quoted2
  }

}

abstract class LinedProperty[A: LineLoadable](
  parser: String => Parser[String],
  property: String) {

  def via(file: File): A = {
    convertFrom(PropertyFile(file))
  }

  def fromResource(resourcePath: String): A = {
    convertFrom(PropertyResource(resourcePath))
  }

  private def convertFrom(source: PropertySource) = {
    val load = implicitly[LineLoadable[A]]
    load by new LineLoader(source, property, parser(property))
  }
}
