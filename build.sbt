import sbt.Keys.libraryDependencies
import WheatDependencies.{`sbt-assembly`, forTests}
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
