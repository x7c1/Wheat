package x7c1.wheat.harvest.values

import x7c1.wheat.harvest.{JavaSourceFactory, JavaSourcesFactory, ParsedResource}

class ValuesSourcesFactory(locations: ValuesLocations) extends JavaSourcesFactory {
  override def createFrom(values: ParsedResource) = {
    val valuesSourceFactory = new JavaSourceFactory(
      targetDir = locations.valuesDst,
      className = values.prefix.ofClass + "Values",
      template = x7c1.wheat.harvest.txt.values.apply,
      partsFactory = new ValuesInterfacePartsFactory(locations.packages)
    )
    val providerSourceFactory = new JavaSourceFactory(
      targetDir = locations.providerDst,
      className = values.prefix.ofClass + "ValuesProvider",
      template = x7c1.wheat.harvest.txt.valuesProvider.apply,
      partsFactory = new ValuesProviderPartsFactory(locations.packages)
    )
    val factories = Seq(
      valuesSourceFactory,
      providerSourceFactory
    )
    factories.map(_.createFrom(values))
  }
}
