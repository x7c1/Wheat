package x7c1.wheat.harvest

import sbt.File


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
