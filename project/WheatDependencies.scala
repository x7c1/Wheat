import sbt._

object WheatDependencies {
  val forTests = Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % Test
  )
}
