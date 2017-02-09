package x7c1.wheat.harvest.layout

import sbt.Def.Initialize
import sbt.{Def, File, InputTask, globFilter, richFile, singleFileFinder}
import x7c1.wheat.harvest.HarvestSettings.harvestLocations
import x7c1.wheat.harvest.{FilesGenerator, HarvestLocations, WheatPackages}

object LayoutGenerator {

  def task: Initialize[InputTask[Unit]] = {
    Def settingDyn generator.value.task
  }

  def generator = Def setting new FilesGenerator(
    finder = locations.value.layoutSrc * "*.xml",
    loader = new LayoutResourceLoader(locations.value.layoutSrc),
    generator = new LayoutSourcesFactory(locations.value)
  )

  def locations: Initialize[LayoutLocations] = {
    Def setting LayoutLocations(harvestLocations.value)
  }
}

case class LayoutLocations(
  locations: HarvestLocations) {

  private val directories = locations.directories

  val packages: WheatPackages = locations.packages

  val layoutSrc: File = directories.starter / "src/main/res/layout"

  val layoutDst: File = {
    directories.glue / "src/main/java" / packages.glueLayout.replace(".", "/")
  }
  val providerDst: File = {
    directories.starter / "src/main/java" / packages.starterLayout.replace(".", "/")
  }

  override def toString = {
    val lines = Seq(
      "(",
      "  source",
      "    layout => " + layoutSrc,
      "  destinations",
      "    holder => " + layoutDst,
      "    provider => " + providerDst,
      ")"
    )
    lines mkString "\n"
  }

}
