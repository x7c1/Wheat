import sbt.Resolver


object WheatDecorators {

  implicit class RichResolvers(xs: Seq[Resolver]) {
    def overwriteBy(resolver: Resolver): Seq[Resolver] = {
      (xs filterNot (_.name == resolver.name)) :+ resolver
    }
  }

}
