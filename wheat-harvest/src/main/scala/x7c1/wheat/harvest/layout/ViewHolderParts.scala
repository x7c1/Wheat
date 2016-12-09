package x7c1.wheat.harvest.layout

import x7c1.wheat.harvest.PackageResolver.toPackage
import x7c1.wheat.harvest.{ResourcePrefix, Indent, ParsedResource, ResourcePartsFactory, WheatPackages, ResourceParts}

trait ViewHolderParts extends ResourceParts {
  def parentClass: String
  def declarePackage: String
  def imports: String
  def fields: String
  def arguments: String
  def assignments: String
}

class ViewHolderPartsFactory (packages: WheatPackages)
  extends ResourcePartsFactory[ViewHolderParts] {

  override def createFrom(layout: ParsedResource): ViewHolderParts = {
    new ViewHolderPartsImpl(packages, layout)
  }
}

private class ViewHolderPartsImpl(packages: WheatPackages, layout: ParsedResource)
  extends ViewHolderParts with Indent {
  override def declarePackage: String = s"package ${packages.glueLayout};"

  override def parentClass: String = {
    layout.prefix.parentClassName getOrElse "RecyclerView.ViewHolder"
  }
  override def imports: String = {
    val x0 = Seq(
      "import android.view.View;"
    )
    val w0 = layout.prefix.parentClassName match {
      case Some(_) => Seq()
      case None => Seq("import android.support.v7.widget.RecyclerView;")
    }
    val x1 = layout.elements map { x => s"import ${toPackage(x.tag)};" }
    (w0 ++ x0 ++ x1).distinct mkString "\n"
  }

  override def fields: String = {
    val x0 = layout.elements map { x => s"public final ${x.tag} ${x.label};" }
    x0 mkString indent(1)
  }

  override def arguments: String = {
    val x0 = layout.elements map { x => s"${x.tag} ${x.label}" }
    val x1 = "View itemView"
    (x1 +: x0) mkString indent(",", 2)
  }

  override def assignments: String = {
    val x0 = layout.elements.map{ x => s"this.${x.label} = ${x.label};"}
    x0 mkString indent(2)
  }

  override def prefix: ResourcePrefix = layout.prefix
}
