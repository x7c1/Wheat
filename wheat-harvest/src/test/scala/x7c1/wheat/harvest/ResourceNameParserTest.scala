package x7c1.wheat.harvest

import org.scalatest.{FlatSpecLike, Matchers}

class ResourceNameParserTest extends FlatSpecLike with Matchers {
   behavior of ResourceNameParser.getClass.getSimpleName

   it can "read prefix from file name" in {
     val Right(prefix) = ResourceNameParser.readPrefix("abcd_ef_ghi.xml")
     prefix.ofClass shouldBe "AbcdEfGhi"
     prefix.ofKey shouldBe "abcd_ef_ghi__"
     prefix.parentClassName shouldBe None

     val Right(prefix2) = ResourceNameParser.readPrefix("xyz_abcd__ef.xml")
     prefix2.ofClass shouldBe "XyzAbcdEf"
     prefix2.ofKey shouldBe "xyz_abcd__ef__"
     prefix2.parentClassName shouldBe Some("XyzAbcd")
   }

  it can "read file name with number" in {
    val Right(x0) = ResourceNameParser.readPrefix("abcd0_ef_ghi.xml")
    x0.ofClass shouldBe "Abcd0EfGhi"
    x0.ofKey shouldBe "abcd0_ef_ghi__"
    x0.parentClassName shouldBe None

    val Right(x1) = ResourceNameParser.readPrefix("abcd_e0f_ghi.xml")
    x1.ofClass shouldBe "AbcdE0fGhi"
    x1.ofKey shouldBe "abcd_e0f_ghi__"
    x1.parentClassName shouldBe None

    val Right(x2) = ResourceNameParser.readPrefix("abcd_e0f__ghi.xml")
    x2.parentClassName shouldBe Some("AbcdE0f")
  }

  it should "fail to invalid file name" in {
     val Left(e0) = ResourceNameParser.readPrefix("0xyz_abcd_ef_ghi.xml")
     e0 shouldBe a[HarvestParserError]

     val Left(e2) = ResourceNameParser.readPrefix("xyz_0abcd.xml")
     e2 shouldBe a[HarvestParserError]
   }
 }
