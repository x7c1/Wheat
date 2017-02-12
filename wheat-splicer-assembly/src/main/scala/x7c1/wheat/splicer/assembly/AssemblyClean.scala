package x7c1.wheat.splicer.assembly

import sbt.Def.{SettingList, SettingsDefinition}
import sbt.Keys.{clean, streams}
import sbtassembly.AssemblyKeys.{assembly, assemblyOutputPath}
import x7c1.wheat.splicer.assembly.AssemblySettingKeys.splicerAssemblyClean
import x7c1.wheat.splicer.lib.FileCleaner


object AssemblyClean {

  def settings: SettingsDefinition = new SettingList(Seq(
    splicerAssemblyClean := {
      val path = (assemblyOutputPath in assembly).value
      FileCleaner.withLogging remove path run streams.value.log
    },
    clean := clean.dependsOn(splicerAssemblyClean).value
  ))

}
