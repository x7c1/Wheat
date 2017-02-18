package x7c1.wheat.harvest

import sbt.Def.{SettingList, SettingsDefinition, inputKey, settingKey}
import sbt.{InputKey, SettingKey}
import x7c1.wheat.harvest.layout.{LayoutGenerator, LayoutLocations, ViewHolderGenerator}
import x7c1.wheat.harvest.values.{ValuesGenerator, ValuesLocations}

object HarvestSettings {

  lazy val harvestLocations: SettingKey[HarvestLocations] = {
    settingKey("Location settings for wheat-harvest plugin")
  }

  lazy val harvestLayout: InputKey[Unit] = {
    inputKey("Generates from res/layout files")
  }

  lazy val harvestValues: InputKey[Unit] = {
    inputKey("Generates from res/values files")
  }

  lazy val harvestViewHolder: InputKey[Unit] = {
    inputKey("Generates ViewHolder files")
  }

  lazy val harvestValuesLocations: SettingKey[ValuesLocations] = {
    settingKey("res/values locations")
  }

  lazy val harvestLayoutLocations: SettingKey[LayoutLocations] = {
    settingKey("res/layout locations")
  }

  def definition: SettingsDefinition = new SettingList(Seq(
    harvestLayout := {
      LayoutGenerator.task.evaluated
    },
    harvestLayoutLocations := {
      LayoutGenerator.locations.value
    },
    harvestValues := {
      ValuesGenerator.task.evaluated
    },
    harvestValuesLocations := {
      ValuesGenerator.locations.value
    },
    harvestViewHolder := {
      ViewHolderGenerator.task.evaluated
    }
  ))
}
