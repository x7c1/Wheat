package x7c1.wheat.splicer.android

import sbt.{File, Logger, Process, richFile}

class RGenerator(
  logger: Logger,
  sdk: AndroidSdk,
  manifest: File,
  sourceDestination: File) {

  def generateFrom(resourceDirectories: Seq[File]): Int = {

    val dirs = resourceDirectories map
      (_.getAbsolutePath) flatMap
      (x => Seq("-S", x))

    val builder = Process apply Seq(
      (sdk.buildTools / "aapt").getAbsolutePath, "package",
      "--auto-add-overlay",
      "-m", "-J", sourceDestination.getAbsolutePath,
      "-M", manifest.getAbsolutePath,
      "-I", (sdk.platforms / "android.jar").getAbsolutePath
    ) ++ dirs

    builder !< logger
  }
}
