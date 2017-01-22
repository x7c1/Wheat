package x7c1.wheat.splicer.android

import sbt.{File, richFile}


trait AndroidSdk {
  def root: File

  def buildTools: File

  def platforms: File

  def extras: File

  def `android-m2repository`: File
}

object AndroidSdk {

  def apply(
    sdkRoot: File,
    buildToolsVersion: String,
    compileSdkVersion: Int): AndroidSdk = {

    AndroidSdkImpl(
      root = validate(sdkRoot),
      buildTools = validate(sdkRoot / "build-tools" / buildToolsVersion),
      platforms = validate(sdkRoot / "platforms" / s"android-$compileSdkVersion"),
      extras = validate(sdkRoot / "extras")
    )
  }

  private def validate(file: File): File = {
    if (file.exists()) {
      file
    } else {
      throw new IllegalArgumentException(s"file not found: $file")
    }
  }

  private case class AndroidSdkImpl(
    root: File,
    buildTools: File,
    platforms: File,
    extras: File) extends AndroidSdk {

    override val `android-m2repository`: File = {
      validate(extras / "android" / "m2repository")
    }
  }

}
