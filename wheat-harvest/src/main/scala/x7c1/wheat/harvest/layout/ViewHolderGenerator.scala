package x7c1.wheat.harvest.layout

import sbt.Def.Initialize
import sbt.{Def, InputTask}
import x7c1.wheat.harvest.layout.LayoutGenerator.locations

object ViewHolderGenerator {

  def task: Initialize[InputTask[Unit]] = Def settingDyn {
    val generator = LayoutGenerator.from(
      locations = locations.value,
      factory = new ViewHolderSourcesFactory(locations.value)
    )
    generator.task
  }
}
