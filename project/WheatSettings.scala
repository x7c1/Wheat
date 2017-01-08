import bintray.BintrayKeys.bintrayRepository
import sbt.Keys._

object WheatSettings {

  lazy val latestVersion = "2.11.7"

  lazy val common = Seq(
    organization := "x7c1",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint"
    )
  ) ++ bintraySettings

  lazy val bintraySettings = Seq(
    bintrayRepository := "android"
  )
}
