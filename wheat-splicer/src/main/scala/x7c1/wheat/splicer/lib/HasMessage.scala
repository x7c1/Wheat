package x7c1.wheat.splicer.lib

trait HasMessage[A] {
  def messageOf(x: A): String
}
