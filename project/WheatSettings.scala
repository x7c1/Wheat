import bintray.BintrayKeys.bintrayRepository
import sbt.Def.SettingList
import sbt.Keys._

object WheatSettings {

  lazy val latestVersion = "2.11.7"

  lazy val common = new SettingList(Seq(
    organization := "x7c1",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint"
    )
  ))

  lazy val forPlugin = new SettingList(
    Seq(
      sbtPlugin := true
    ) ++ common
      ++ forBintray
      ++ PublishLocalSnapshot.definition
  )

  lazy val forBintray = new SettingList(Seq(
    bintrayRepository := "android"
  ))
}
