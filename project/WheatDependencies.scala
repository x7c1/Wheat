import sbt._

object WheatDependencies {

  lazy val forTests = Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % Test
  )
  lazy val `sbt-assembly` = {
    "com.eed3si9n" % "sbt-assembly" % "0.14.3"
  }
}
