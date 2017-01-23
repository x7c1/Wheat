package x7c1.wheat.splicer.android

import sbt.File

trait LineLoadable[A] {
  def by(x: LineLoader): A
}

object LineLoadable {

  implicit object string extends LineLoadable[String] {
    override def by(x: LineLoader): String = {
      x.requireSingle()
    }
  }

  implicit object int extends LineLoadable[Int] {
    override def by(x: LineLoader): Int = {
      x.requireSingle().toInt
    }
  }

  implicit object file extends LineLoadable[File] {
    override def by(x: LineLoader): File = {
      new File(x.requireSingle())
    }
  }

  implicit object strings extends LineLoadable[Seq[String]] {
    override def by(x: LineLoader): Seq[String] = {
      x.loadMultiple()
    }
  }

}
