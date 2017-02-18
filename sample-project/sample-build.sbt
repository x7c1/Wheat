import x7c1.wheat.harvest.{HarvestSettings, HarvestLocations}
import x7c1.wheat.harvest.HarvestSettings.harvestLocations

lazy val root = Project(id = "root", base = file(".")).
  settings(HarvestSettings.definition).
  settings(
    harvestLocations := HarvestLocations(
      starterPackage = "x7c1.sample",
      starterDirectory = file("sample-starter"),
      gluePackage = "x7c1.sample.glue",
      glueDirectory = file("sample-glue")
    )
  )
