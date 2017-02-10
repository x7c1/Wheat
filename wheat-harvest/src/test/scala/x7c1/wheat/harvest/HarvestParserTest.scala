package x7c1.wheat.harvest

import org.scalatest.{FlatSpecLike, Matchers}

class HarvestParserTest extends FlatSpecLike with Matchers {
  behavior of HarvestParser.getClass.getSimpleName

  it can "camelize strings except for head string" in {
    val Right(x0) = HarvestParser.camelizeTail("abcd_ef_ghi")
    x0 shouldBe "abcdEfGhi"
  }
}
