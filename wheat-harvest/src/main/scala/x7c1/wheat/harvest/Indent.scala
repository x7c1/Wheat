package x7c1.wheat.harvest

trait Indent {
  def indent(n: Int) = "\n" + ("    " * n)
  def indent(tail: String, n: Int) = tail + "\n" + ("    " * n)
}
