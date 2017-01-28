package x7c1.wheat.splicer.core

import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import sbt.{file, globFilter, richFile, singleFileFinder}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.android.PropertyLoader.{buildToolsVersion, compileSdkVersion, dependencies, sdkRoot}
import x7c1.wheat.splicer.core.logger.Logging

class CacheSplicersTest extends FlatSpecLike
  with Matchers with BeforeAndAfterAll with Logging with SplicerSettings with CustomMatchers {

  override protected def beforeAll(): Unit = {
    splicers.cleanAll run logger
    splicers.expandAll run logger
  }

  behavior of "expandAll method"

  ".sourceDirectories" must "contain generated R.java files" in {
    val expected = Seq(
      unmanaged / "appcompat-v7/src-generated/android/support/v7/appcompat/R.java",
      unmanaged / "cardview-v7/src-generated/android/support/v7/cardview/R.java",
      unmanaged / "design/src-generated/android/support/design/R.java",
      unmanaged / "recyclerview-v7/src-generated/android/support/v7/recyclerview/R.java"
    )
    val actualPaths = splicers.sourceDirectories.map(_ ** "R.java").flatMap(_.getPaths)
    actualPaths should containAllOf(expected map (_.absolutePath))
  }

  ".classpath" must "contain .jar files extracted from .aar files" in {
    val expected = Seq(
      unmanaged / "recyclerview-v7/classes.jar",
      unmanaged / "appcompat-v7/classes.jar",
      unmanaged / "design/classes.jar",
      unmanaged / "cardview-v7/classes.jar",
      unmanaged / "support-v4/classes.jar",
      unmanaged / "support-v4/libs/internal_impl-23.4.0.jar",
      unmanaged / "animated-vector-drawable/classes.jar",
      unmanaged / "support-vector-drawable/classes.jar",
      unmanaged / "recyclerview-v7/classes.jar"
    )
    val actualPaths = splicers.classpath.map(_.data.absolutePath)
    actualPaths should containAllOf(expected map (_.absolutePath))
  }

  it must "contain existing .jar files" in {
    val expected = Seq(
      sdk.platforms / "android.jar",
      sdk.platforms / "uiautomator.jar",
      sdk.`android-m2repository` /
        "com/android/support/support-annotations" /
        "23.4.0/support-annotations-23.4.0.jar"
    )
    val actualPaths = splicers.classpath.map(_.data.absolutePath)
    actualPaths should containAllOf(expected map (_.absolutePath))
  }

}

trait SplicerSettings {
  val project = file("./sample-splicer-client")
  val sdk = AndroidSdk(
    sdkRoot = sdkRoot via (project / "local.properties"),
    buildToolsVersion = buildToolsVersion fromResource "/build.gradle",
    compileSdkVersion = compileSdkVersion fromResource "/build.gradle"
  )
  val unmanaged = (project / "libs-expanded").getCanonicalFile
  val splicers = {
    val factory = new CacheSplicers.Factory(unmanaged, sdk)
    factory create (dependencies fromResource "/target.gradle")
  }
}
