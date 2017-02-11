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

  lazy val forPlugin = new SettingList(common ++
    PublishLocalSnapshot.definition ++
    Seq(
      sbtPlugin := true,
      bintrayRepository := "sbt-plugins"
    )
  )
}
