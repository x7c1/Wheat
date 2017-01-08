package x7c1.wheat.splicer.maven


case class PomProject(
  groupId: String,
  artifactId: String,
  version: String,
  packaging: Option[String],
  dependencies: Seq[PomProjectDependency]
)

case class PomProjectDependency(
  groupId: String,
  artifactId: String,
  version: String,
  moduleType: Option[String]
)
