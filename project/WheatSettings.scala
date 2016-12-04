import sbt.Keys._

object WheatSettings {

  val latestVersion = "2.11.7"

  val common = Seq(
    organization := "x7c1",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint"
    )
  )

}
