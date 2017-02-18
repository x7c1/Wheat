package x7c1.wheat.harvest.values

import x7c1.wheat.harvest.{ResourceParts, Indent, ParsedResource, ResourcePartsFactory, Packages, ResourcePrefix}

trait ValuesInterfaceParts extends ResourceParts {
  def declarePackage: String
  def prefix: ResourcePrefix
  def methods: String
}

class ValuesInterfacePartsFactory(packages: Packages)
  extends ResourcePartsFactory[ValuesInterfaceParts]{

  override def createFrom(values: ParsedResource): ValuesInterfaceParts = {
    new ValuesInterfacePartsImpl(packages, values)
  }
}

private class ValuesInterfacePartsImpl(packages: Packages, values: ParsedResource)
  extends ValuesInterfaceParts with Indent {

  override def declarePackage: String = s"package ${packages.glueValues};"

  import TypeResolver.toType

  override def methods: String = {
    val x0 = values.elements map { x => s"${toType(x.tag)} ${x.label}();" }
    x0 mkString indent(1)
  }

  override def prefix: ResourcePrefix = values.prefix
}

object TypeResolver {
  def toType(tag: String): String = tag match {
    case "string" => "String"
    case "bool" => "Boolean"
  }
}
