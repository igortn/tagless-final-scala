package sandbox

import cats.Monad
import cats.effect.IO

trait Console[F[_]] {
  def readLine: F[String]
  def writeLine(line: String): F[Unit]
}

object Console {
  def apply[F[_]](implicit inst: Console[F]): Console[F] = inst
}

object Main {

  object Output {
    val whoAreYou = "who are you?"
    def niceToMeetYou(name: String) = s"nice to meet you $name"
    val doYouNeedAnything = "do you need anything?"
    def bye(name: String) = s"bye then, $name, see you later"
  }

  implicit class Syntax[F[_], A](fa: F[A]) {
    def map[B](ab: A => B)(implicit m: Monad[F]): F[B] = m.map(fa)(ab)
    def flatMap[B](afb: A => F[B])(implicit m: Monad[F]): F[B] = m.flatMap(fa)(afb)
  }

  implicit val consoleIoInst: Console[IO] =
    new Console[IO] {
      def readLine: IO[String] = IO { scala.io.StdIn.readLine() }
      def writeLine(line: String): IO[Unit] = IO { println(line) }
    }

  def main[F[_] : Console : Monad]: F[Unit] = {
    import Output._
    for {
      _ <- Console[F].writeLine(whoAreYou)
      name <- Console[F].readLine
      _ <- Console[F].writeLine(niceToMeetYou(name))
      _ <- Console[F].writeLine(doYouNeedAnything)
      _ <- Console[F].readLine
      _ <- Console[F].writeLine(bye(name))
    } yield ()
  }

  def mainIO: IO[Unit] = main[IO]
}
