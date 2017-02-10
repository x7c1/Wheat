package x7c1.wheat.harvest.layout

import sbt.Def.Initialize
import sbt.{Def, InputTask, globFilter, singleFileFinder}
import x7c1.wheat.harvest.FilesGenerator

object ViewHolderGenerator {

  def task: Initialize[InputTask[Unit]] = Def settingDyn {
    val locations = LayoutGenerator.locations.value
    from(locations).task
  }

  def from(locations: LayoutLocations) = new FilesGenerator(
    finder = locations.layoutSrc * "*.xml",
    loader = new LayoutResourceLoader(locations.layoutSrc),
    generator = new ViewHolderSourcesFactory(locations)
  )
}
