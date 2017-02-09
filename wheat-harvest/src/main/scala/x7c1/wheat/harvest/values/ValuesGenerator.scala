package x7c1.wheat.harvest.values

import sbt.Def.Initialize
import sbt.{Def, File, InputTask, globFilter, richFile, singleFileFinder}
import x7c1.wheat.harvest.HarvestSettings.harvestLocations
import x7c1.wheat.harvest.{FilesGenerator, HarvestLocations, Directories, Packages}

object ValuesGenerator {

  def task: Initialize[InputTask[Unit]] = {
    Def settingDyn generator.value.task
  }

  def generator = Def setting new FilesGenerator(
    finder = locations.value.valuesSrc * "*.xml",
    loader = new ValuesResourceLoader(locations.value.valuesSrc),
    generator = new ValuesSourcesFactory(locations.value)
  )

  def locations: Initialize[ValuesLocations] = {
    Def setting ValuesLocations(harvestLocations.value)
  }
}

case class ValuesLocations(
  locations: HarvestLocations) {

  private val directories = locations.directories

  val packages: Packages = locations.packages

  val valuesSrc: File = directories.starter / "src/main/res/values"

  val valuesDst: File = {
    directories.glue / "src/main/java" / packages.glueValues.replace(".", "/")
  }
  val providerDst: File = {
    directories.starter / "src/main/java" / packages.starterValues.replace(".", "/")
  }

  override def toString = {
    val lines = Seq(
      "(",
      "  source",
      "    values => " + valuesSrc,
      "  destinations",
      "    interface => " + valuesDst,
      "    provider => " + providerDst,
      ")"
    )
    lines mkString "\n"
  }
}
