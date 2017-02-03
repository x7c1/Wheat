import sbt.Keys.libraryDependencies
import WheatDependencies.{`sbt-assembly`, forTests, logback}
import WheatSettings.forPlugin


lazy val `wheat-harvest` = project.
  settings(forPlugin).
  settings(
    name := "wheat-harvest",
    version := "0.1.0",
    libraryDependencies ++= forTests
  ).
  dependsOn(`wheat-parser`).
  enablePlugins(SbtTwirl)

lazy val `wheat-parser` = project.
  settings(forPlugin).
  settings(
    version := "0.1.0"
  )

lazy val `wheat-splicer` = project.
  settings(forPlugin).
  settings(
    libraryDependencies ++= forTests ++ Seq(
      logback % Test
    ),
    version := "0.1.1"
  )

lazy val `wheat-splicer-assembly` = project.
  settings(forPlugin).
  settings(addSbtPlugin(`sbt-assembly`)).
  settings(
    version := "0.1.1"
  ).
  dependsOn(`wheat-splicer`)
