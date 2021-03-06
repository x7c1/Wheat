package x7c1.wheat.harvest

import sbt.Def.Initialize
import sbt.{Def, InputTask, Keys, PathFinder, ProcessLogger}
import x7c1.wheat.harvest.HarvestParser.selectFrom

class FilesGenerator(
  finder: PathFinder,
  loader: ResourceLoader,
  generator: JavaSourcesFactory) {

  def task: Initialize[InputTask[Unit]] =
    Def inputTask run(
      logger = LabeledLogger(
        label = "harvest",
        delegateTo = Keys.streams.value.log
      ),
      loadFileNames = () => selectFrom(finder).parsed
    )

  def run(logger: ProcessLogger, loadFileNames: () => Seq[String]): Unit = {
    val names = loadFileNames()

    logger info "selected files"
    names.map(" * " + _) foreach (logger info _)

    val list = names map loader.load map (_.right map generator.createFrom)
    logger info "generated files"

    list foreach {
      case Right(sources) =>
        sources foreach { source =>
          JavaSourceWriter write source
          logger info " * " + source.file.getPath
        }
      case Left(errors) => errors foreach (logger error _.message)
    }
  }
}
