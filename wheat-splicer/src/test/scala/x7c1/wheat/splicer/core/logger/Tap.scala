package x7c1.wheat.splicer.core.logger

object Tap {

  object implicits {

    implicit class Provider[A](target: A) {
      def tap(f: (A => Unit)*): A = {
        f foreach (_ (target))
        target
      }
    }

  }

}
