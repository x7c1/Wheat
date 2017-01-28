package x7c1.wheat.splicer.core

import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import sbt.{file, globFilter, richFile, singleFileFinder}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.android.PropertyLoader.{buildToolsVersion, compileSdkVersion, dependencies, sdkRoot}
import x7c1.wheat.splicer.core.logger.Logging

class CacheSplicersTest extends FlatSpecLike
  with Matchers with BeforeAndAfterAll with Logging with SplicerSettings {

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
    expected.map(_.getPath) foreach { path =>
      actualPaths should contain(path)
    }
  }

    )
    val splicers = factory create (dependencies fromResource "/target.gradle")

    println(splicers.sourceDirectories)

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
