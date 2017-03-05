import WheatDecorators.RichResolvers
import bintray.BintrayKeys.bintrayRepository
import sbt.Def.SettingList
import sbt.Keys._
import sbt.Resolver.bintrayIvyRepo

object WheatSettings {

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
    Seq(
      sbtPlugin := true,
      bintrayRepository := "sbt-plugins",
      resolvers := {
        /*
          remove warning "Multiple resolvers having different access mechanism"
          caused by sbt-bintray(0.3.0) which automatically
          sets resolvers := bintrayRepo("x7c1", "sbt-plugins").
         */
        resolvers.value overwriteBy bintrayIvyRepo("x7c1", "sbt-plugins")
      }
    )
  )

}
