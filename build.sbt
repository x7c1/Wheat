import sbt.Keys.libraryDependencies

lazy val `wheat-harvest` = project.
  settings(WheatSettings.common:_*).
  settings(
    name := "wheat-harvest",
    version := "0.1.0",
    sbtPlugin := true,
    logLevel in assembly := Level.Error,
    libraryDependencies ++= WheatDependencies.forTests
  ).
  dependsOn(`wheat-parser`).
  enablePlugins(SbtTwirl)

lazy val `wheat-parser` = project.
  settings(WheatSettings.common:_*).
  settings(
    version := "0.1.0",
    sbtPlugin := true
  )