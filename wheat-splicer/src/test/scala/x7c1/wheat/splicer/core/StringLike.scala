package x7c1.wheat.splicer.core

trait StringLike[A]{
  def from(x: A): String
}

object StringLike {
  implicit object str extends StringLike[String]{
    override def from(x: String): String = x
  }
}
