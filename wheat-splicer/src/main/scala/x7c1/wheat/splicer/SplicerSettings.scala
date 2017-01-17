package x7c1.wheat.splicer

import sbt.Keys.{clean, streams, unmanagedBase, unmanagedJars, unmanagedSourceDirectories}
import sbt.{Compile, Def, SettingsDefinition, richFile, settingKey, taskKey}

import x7c1.wheat.splicer.SplicerKeys.{splicerClean, splicerDependencies, splicerExpand, splicerSdk}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.core.CacheSplicers


object SplicerKeys {

  val splicerDependencies = settingKey[Seq[String]]("dependencies")

  val splicerSdk = settingKey[AndroidSdk]("Android SDK")

  val splicerExpand = taskKey[Unit]("expand archives according to dependencies")

  val splicerClean = taskKey[Unit]("delete expanded files")
}

object SplicerSettings {

  private lazy val splicers = Def setting {
    val factory = new CacheSplicers.Factory(
      cacheDirectory = splicerSdk.value.extras / "android/m2repository",
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
