package x7c1.wheat.splicer.core

import org.scalatest.matchers.{MatchResult, Matcher}

trait CustomMatchers {
  def containAllOf[A: Manifest : StringLike](expected: Seq[A]): Matcher[Seq[A]] = {
    val asString = implicitly[StringLike[A]]
    Matcher { actual =>
      val unknown = expected.find(!actual.contains(_))
      val toMessage = (name: A) => {
        val first = Seq(asString from name, "is not included in")
        first ++ (actual map asString.from) mkString "\n"
      }
      MatchResult(
        matches = unknown.isEmpty,
        rawFailureMessage = unknown map toMessage getOrElse "not found",
        rawNegatedFailureMessage = "all items matched"
      )
    }
  }
}
