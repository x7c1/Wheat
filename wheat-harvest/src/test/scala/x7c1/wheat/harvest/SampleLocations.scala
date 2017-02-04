package x7c1.wheat.harvest

import sbt.{file, richFile}
import x7c1.wheat.harvest.layout.LayoutLocations
import x7c1.wheat.harvest.values.ValuesLocations

object SampleLocations {

  def packages = WheatPackages(
    starter = "x7c1.wheat.sample",
    starterLayout = "x7c1.wheat.sample.res.layout",
    starterValues = "x7c1.wheat.sample.res.values",
    glueLayout = "x7c1.wheat.sample.glue.res.layout",
    glueValues = "x7c1.wheat.sample.glue.res.values"
  )

  def directories = WheatDirectories(
    starter = file("sample-project") / "sample-starter",
    glue = file("sample-project") / "sample-glue"
  )

  def layout = LayoutLocations(
    packages = packages,
    directories = directories
  )

  def values = ValuesLocations(
    packages = packages,
    directories = directories
  )
}
