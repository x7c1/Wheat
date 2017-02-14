package x7c1.wheat.harvest.layout

import sbt.Def.Initialize
import sbt.{Def, File, InputTask, globFilter, richFile, singleFileFinder}
import x7c1.wheat.harvest.HarvestSettings.harvestLocations
import x7c1.wheat.harvest.{FilesGenerator, HarvestLocations, JavaSourcesFactory, Packages}

object LayoutGenerator {

  def task: Initialize[InputTask[Unit]] = Def settingDyn {
    val generator = from(
      locations = locations.value,
      factory = new LayoutSourcesFactory(locations.value)
    )
    generator.task
  }

  def from(locations: LayoutLocations, factory: JavaSourcesFactory) =
    new FilesGenerator(
      finder = locations.layoutSrc * "*.xml",
      loader = new LayoutResourceLoader(locations.layoutSrc),
      generator = factory
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
    s"""(
       |  source:
       |    layout: $layoutSrc
       |  destinations:
       |    holder: $layoutDst
       |    provider: $providerDst
       |)
     """.stripMargin
  }
}
