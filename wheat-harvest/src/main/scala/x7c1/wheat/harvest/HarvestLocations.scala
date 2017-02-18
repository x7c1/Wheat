package x7c1.wheat.harvest

import sbt.File

trait HarvestLocations {

  def directories: Directories

  def packages: Packages

  override def toString =
    s"""(
       |  starter:
       |    directory:
       |      ${directories.starter}
       |    packages:
       |      layout: ${packages.starterLayout}
       |      values: ${packages.starterValues}
       |  glue:
       |    directory:
       |      ${directories.glue}
       |    packages:
       |      layout: ${packages.glueLayout}
       |      values: ${packages.glueValues}
       |)
     """.stripMargin
}

object HarvestLocations {

  def apply(
    starterPackage: String,
    starterDirectory: File,
    gluePackage: String,
    glueDirectory: File): HarvestLocations = new HarvestLocations {

    override def directories = Directories(
      starter = starterDirectory,
      glue = glueDirectory
    )

    override def packages = Packages(
      starter = starterPackage,
      starterLayout = s"$starterPackage.res.layout",
      starterValues = s"$starterPackage.res.values",
      glueLayout = s"$gluePackage.res.layout",
      glueValues = s"$gluePackage.res.values"
    )
  }

  def apply(
    directories: Directories,
    packages: Packages): HarvestLocations = {

    HarvestLocationsImpl(directories, packages)
  }

  private case class HarvestLocationsImpl(
    directories: Directories,
    packages: Packages) extends HarvestLocations

}

case class Directories(
  starter: File,
  glue: File
)

case class Packages(
  starter: String,
  starterLayout: String,
  starterValues: String,
  glueLayout: String,
  glueValues: String
)
