package x7c1.wheat.splicer.assembly

import sbt.Def.{SettingList, SettingsDefinition}
import sbt.Keys.{thisProject, unmanagedBase, unmanagedJars}
import sbt.{File, Project, ResolvedProject, richFile, richFiles, singleFileFinder, taskKey}
import sbtassembly.AssemblyKeys.{assembly, assemblyJarName, assemblyOption, assemblyOutputPath}
import sbtassembly.AssemblyPlugin.autoImport.assemblyExcludedJars
import x7c1.wheat.splicer.SplicerKeys.{splicerDependencies, splicerExpand}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.android.PropertyLoader.{buildToolsVersion, compileSdkVersion, dependencies, sdkRoot}
import x7c1.wheat.splicer.{SplicerKeys, SplicerSettings}


object AssemblySettingKeys {
  val splicerAssemblyClean = taskKey[Unit]("remove assembled jar")
}

object AssemblySettings {

  def forProvider(
    assemblyDirectory: ResolvedProject => File,
    splicerDirectory: ResolvedProject => File,
    localProperties: File,
    buildGradle: File,
    dependenciesGradle: File): SettingsDefinition = {

    val settings = Seq(
      SplicerKeys.splicerSdk := AndroidSdk(
        sdkRoot = sdkRoot via localProperties,
        buildToolsVersion = buildToolsVersion via buildGradle,
        compileSdkVersion = compileSdkVersion via buildGradle
      ),
      unmanagedBase in splicerExpand := {
        splicerDirectory(thisProject.value)
      },
      splicerDependencies := {
        dependencies via dependenciesGradle
      },
      assembly := {
        assembly.dependsOn(splicerExpand).value
      },
      assemblyOption in assembly ~= {
        _ copy (includeScala = false)
      },
      assemblyOutputPath in assembly := {
        assemblyDirectory(thisProject.value) / (assemblyJarName in assembly).value
      }
    )
    new SettingList(settings ++
      SplicerSettings.all ++
      AssemblyClean.settings
    )
  }

  def forClient(providerProject: => Project): SettingsDefinition = {
    val settings = Seq(
      unmanagedJars in sbt.Compile ++= {
        (assemblyOutputPath in assembly in providerProject).value.get.classpath
      },
      assemblyExcludedJars in assembly ++= {
        (assemblyOutputPath in assembly in providerProject).value.get.classpath
      },
      assemblyOption in assembly ~= {
        _ copy (includeScala = false)
      }
    )
    new SettingList(settings)
  }
}
