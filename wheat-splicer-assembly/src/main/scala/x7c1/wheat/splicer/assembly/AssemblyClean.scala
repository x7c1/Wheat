package x7c1.wheat.splicer.assembly

import sbt.Def
import sbt.Def.{SettingList, SettingsDefinition}
import sbt.Keys.clean
import sbtassembly.AssemblyKeys.{assembly, assemblyOutputPath}
import x7c1.wheat.splicer.assembly.AssemblySettingKeys.splicerAssemblyClean
import x7c1.wheat.splicer.lib.FileCleaner


object AssemblyClean {

  def settings: SettingsDefinition = new SettingList(Seq(
    splicerAssemblyClean := assemblyClean.value,
    clean := clean.dependsOn(splicerAssemblyClean).value
  ))

  private val assemblyClean = Def taskDyn {
    FileCleaner.withLogging remove (assemblyOutputPath in assembly).value
  }
}
