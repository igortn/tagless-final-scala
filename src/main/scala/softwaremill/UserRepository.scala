package softwaremill

import java.util.UUID

import cats.Monad

case class User(id: UUID,
                name: String,
                email: String,
                loyaltyPoints: Int)

trait UserRepository[F[_]] {
  def findUser(id: UUID): F[Option[User]]
  def updateUser(u: User): F[Unit]
}

object UserRepository {
  def apply[F[_]](implicit inst: UserRepository[F]): UserRepository[F] = inst
}

trait EmailService[F[_]] {
  def sendEmail(email: String, subject: String, body: String)
}

object EmailService {
  def apply[F[_]](implicit inst: EmailService[F]): EmailService[F] = inst
}

object syntax {
  implicit class Monadic[F[_], A](fa: F[A]) {
    def map[B](ab: A => B)(implicit m: Monad[F]): F[B] = m.map(fa)(ab)
    def flatMap[B](afb: A => F[B])(implicit m: Monad[F]): F[B] = m.flatMap(fa)(afb)
  }
}