package x7c1.wheat.harvest

import sbt.{file, richFile}
import x7c1.wheat.harvest.layout.LayoutLocations
import x7c1.wheat.harvest.values.ValuesLocations

object SampleLocations {

  def packages = Packages(
    starter = "x7c1.wheat.sample",
    starterLayout = "x7c1.wheat.sample.res.layout",
    starterValues = "x7c1.wheat.sample.res.values",
    glueLayout = "x7c1.wheat.sample.glue.res.layout",
    glueValues = "x7c1.wheat.sample.glue.res.values"
  )

  def directories = Directories(
    starter = file("sample-project") / "sample-starter",
    glue = file("sample-project") / "sample-glue"
  )

  def layout = LayoutLocations(HarvestLocations(
    packages = packages,
    directories = directories
  ))

  def values = ValuesLocations(HarvestLocations(
    packages = packages,
    directories = directories
  ))
}
