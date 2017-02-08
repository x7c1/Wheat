import x7c1.wheat.harvest.{WheatDirectories, WheatPackages, WheatSettings}
import x7c1.wheat.harvest.WheatSettings.{directories, packages, wheat}

lazy val root = Project(id = "root", base = file(".")).
  settings(WheatSettings.all:_*).
  settings(
    packages in wheat := WheatPackages(
      starter = "x7c1.sample",
      starterLayout = "x7c1.sample.res.layout",
      starterValues = "x7c1.sample.res.values",
      glueLayout = "x7c1.sample.glue.res.layout",
      glueValues = "x7c1.sample.glue.res.values"
    ),
    directories in wheat := WheatDirectories(
      starter = file("sample-starter"),
      glue = file("sample-glue")
    )
  )
