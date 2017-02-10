package x7c1.wheat.harvest.layout

import sbt.Def.Initialize
import sbt.{Def, File, InputTask, globFilter, richFile, singleFileFinder}
import x7c1.wheat.harvest.HarvestSettings.harvestLocations
import x7c1.wheat.harvest.{FilesGenerator, HarvestLocations, Packages}

object LayoutGenerator {

  def task: Initialize[InputTask[Unit]] = Def settingDyn {
    from(locations.value).task
  }

  def from(locations: LayoutLocations) = new FilesGenerator(
    finder = locations.layoutSrc * "*.xml",
    loader = new LayoutResourceLoader(locations.layoutSrc),
    generator = new LayoutSourcesFactory(locations)
  )

  def locations: Initialize[LayoutLocations] = {
    Def setting LayoutLocations(harvestLocations.value)
  }
}

case class LayoutLocations(
  locations: HarvestLocations) {

  private val directories = locations.directories

  val packages: Packages = locations.packages

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
