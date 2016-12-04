package x7c1.wheat.harvest.layout

import sbt._
import x7c1.wheat.harvest.FilesGenerator
import x7c1.wheat.harvest.layout.LayoutGenerator.locations

object ViewHolderGenerator {

  def task: Def.Initialize[InputTask[Unit]] = Def settingDyn generator.value.task

  def generator = Def setting new FilesGenerator(
    finder = locations.value.layoutSrc * "*.xml",
    loader = new LayoutResourceLoader(locations.value.layoutSrc),
    generator = new ViewHolderSourcesFactory(locations.value)
  )
}
