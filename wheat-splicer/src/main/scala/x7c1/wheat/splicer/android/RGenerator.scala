package x7c1.wheat.splicer.android

import sbt.{File, Process, ProcessBuilder, richFile}

object RGenerator {
  def apply(
    sdk: AndroidSdk,
    manifest: File,
    sourceDestination: File): RGenerator = {

    new RGenerator(sdk, manifest, sourceDestination)
  }
}

class RGenerator private (
  sdk: AndroidSdk,
  manifest: File,
  sourceDestination: File) {

  def generateFrom(resourceDirectories: Seq[File]): ProcessBuilder = {
    val dirs = resourceDirectories map
      (_.getAbsolutePath) flatMap
      (x => Seq("-S", x))

    Process apply Seq(
      (sdk.buildTools / "aapt").getAbsolutePath, "package",
      "--auto-add-overlay",
      "-m", "-J", sourceDestination.getAbsolutePath,
      "-M", manifest.getAbsolutePath,
      "-I", (sdk.platforms / "android.jar").getAbsolutePath
    ) ++ dirs
  }
}
