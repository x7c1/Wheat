package x7c1.wheat.splicer.core

import org.scalatest.{FlatSpecLike, Matchers}
import sbt.{file, richFile}
import x7c1.wheat.splicer.android.AndroidSdk
import x7c1.wheat.splicer.android.PropertyLoader.{buildToolsVersion, compileSdkVersion, dependencies, sdkRoot}
import x7c1.wheat.splicer.core.logger.Logging

class CacheSplicersTest extends FlatSpecLike with Matchers with Logging {

  behavior of CacheSplicers.getClass.getSimpleName

  it can "expand aar files" in {

    logger info "hello..."

    val project = file("./sample-splicer-client")
    val sdk = AndroidSdk(
      sdkRoot = sdkRoot via (project / "local.properties"),
      buildToolsVersion = buildToolsVersion fromResource "/build.gradle",
      compileSdkVersion = compileSdkVersion fromResource "/build.gradle"
    )
    val factory = new CacheSplicers.Factory(
      sdk = sdk,
      unmanagedDirectory = project / "libs-expanded"
    )
    val splicers = factory create (dependencies fromResource "/target.gradle")

    println(splicers.sourceDirectories)

    //    splicers.cleanAll run logger
    //    splicers.expandAll run logger

    true shouldBe true
  }

}
