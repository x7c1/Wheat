package x7c1.wheat.splicer

import sbt.Keys.{clean, streams, unmanagedBase, unmanagedJars, unmanagedSourceDirectories}
import sbt.{Compile, Def, SettingKey, SettingsDefinition, TaskKey, settingKey, taskKey}
import x7c1.wheat.splicer.SplicerKeys.{splicerClean, splicerDependencies, splicerExpand, splicerSdk}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.core.CacheSplicers


object SplicerKeys {

  val splicerDependencies: SettingKey[Seq[String]] = {
    settingKey("Dependencies for which splicerExpand runs")
  }
  val splicerSdk: SettingKey[AndroidSdk] = {
    settingKey("Android SDK files to run splicerExpand")
  }
  val splicerExpand: TaskKey[Unit] = {
    taskKey("Expands archives targeted by splicerDependencies")
  }
  val splicerClean: TaskKey[Unit] = {
    taskKey("Deletes expanded files")
  }
}

object SplicerSettings {

  private lazy val splicers = Def setting {
    val factory = new CacheSplicers.Factory(
      unmanagedDirectory = (unmanagedBase in splicerExpand).value,
      sdk = splicerSdk.value
    )
    factory create splicerDependencies.value
  }

  private def tasks = Seq(
    splicerClean := {
      splicers.value.cleanAll run streams.value.log
    },
    splicerExpand := {
      splicers.value.expandAll run streams.value.log
    },
    clean := {
      clean.value
      splicerClean.value
    }
  )

  private def settings = Seq(
    (unmanagedSourceDirectories in Compile) ++= {
      splicers.value.sourceDirectories
    },
    (unmanagedJars in Compile) ++= {
      splicers.value.classpath
    }
  )

  def all: SettingsDefinition = {
    new sbt.Def.SettingList(tasks ++ settings)
  }

}
