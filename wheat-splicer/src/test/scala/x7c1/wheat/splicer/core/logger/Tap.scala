package x7c1.wheat.splicer.core.logger

object Tap {

  object implicits {

    implicit class Provider[A](val target: A) extends AnyVal {
      def tap(f: (A => Unit)*): A = {
        f foreach (_ (target))
        target
      }
    }

  }

}
