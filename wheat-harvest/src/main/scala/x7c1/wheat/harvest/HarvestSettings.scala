package x7c1.wheat.harvest

import sbt.Configurations.config
import sbt.{Def, File}
import x7c1.wheat.harvest.layout.{ViewHolderGenerator, LayoutLocations, LayoutGenerator}
import x7c1.wheat.harvest.values.{ValuesLocations, ValuesGenerator}

object HarvestSettings {

  lazy val packages = Def.settingKey[WheatPackages]("project packages")
  lazy val directories = Def.settingKey[WheatDirectories]("project directories")

  lazy val generateLayout = Def.inputKey[Unit]("Generates res/layout files")
  lazy val generateValues = Def.inputKey[Unit]("Generates res/values files")
  lazy val generateViewHolder = Def.inputKey[Unit]("Generates ViewHolder files")

  lazy val valuesLocations = Def.settingKey[ValuesLocations]("res/values locations")
  lazy val layoutLocations = Def.settingKey[LayoutLocations]("res/layout locations")

  lazy val wheat = config("wheat")

  def all = Seq(
    generateLayout in wheat := LayoutGenerator.task.evaluated,
    generateValues in wheat := ValuesGenerator.task.evaluated,
    generateViewHolder in wheat := ViewHolderGenerator.task.evaluated,

    valuesLocations in wheat := ValuesGenerator.locations.value,
    layoutLocations in wheat := LayoutGenerator.locations.value
  )
}
