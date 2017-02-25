import WheatDependencies.{`sbt-assembly`, forTests, logback}
import WheatSettings.forPlugin
import sbt.Keys.libraryDependencies


lazy val `chaff-parser` = project.
  settings(forPlugin).
  settings(
    version := "0.1.0"
  )

lazy val `wheat-harvest` = project.
  settings(forPlugin).
  settings(
    version := "0.2.0",
    libraryDependencies ++= forTests
  ).
  dependsOn(`chaff-parser`).
  enablePlugins(SbtTwirl)

lazy val `wheat-splicer` = project.
  settings(forPlugin).
  settings(addSbtPlugin("x7c1" % "chaff-process" % "0.1.0")).
  settings(
    libraryDependencies ++= forTests ++ Seq(
      logback % Test
    ),
    version := "0.2.0"
  )

lazy val `wheat-splicer-assembly` = project.
  settings(forPlugin).
  settings(addSbtPlugin(`sbt-assembly`)).
  settings(
    version := "0.2.0"
  ).
  dependsOn(`wheat-splicer`)
