package x7c1.wheat.harvest

import sbt.Def.settingKey
import sbt.{Def, SettingKey}
import x7c1.wheat.harvest.layout.{LayoutGenerator, LayoutLocations, ViewHolderGenerator}
import x7c1.wheat.harvest.values.{ValuesGenerator, ValuesLocations}

object HarvestSettings {

  lazy val harvestLocations: SettingKey[HarvestLocations] = {
    settingKey("Location settings for wheat-harvest plugin")
  }

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

    valuesLocations := ValuesGenerator.locations.value,
    layoutLocations := LayoutGenerator.locations.value
  )
}
