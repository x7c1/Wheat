package x7c1.wheat.harvest

import sbt.File

trait HarvestLocations {

  def directories: WheatDirectories

  def packages: WheatPackages
}

object HarvestLocations {

  def apply(
    directories: WheatDirectories,
    packages: WheatPackages): HarvestLocations = {

    HarvestLocationsImpl(directories, packages)
  }

  private case class HarvestLocationsImpl(
    directories: WheatDirectories,
    packages: WheatPackages) extends HarvestLocations

}

case class WheatDirectories(
  starter: File,
  glue: File
)

case class WheatPackages(
  starter: String,
  starterLayout: String,
  starterValues: String,
  glueLayout: String,
  glueValues: String
)
