package x7c1.wheat.splicer.core.logger

trait FactoryFor[A] {
  def apply[X](x: X)(implicit f: X => A): A = f(x)
}
