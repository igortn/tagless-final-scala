package softwaremill

import syntax._

import java.util.UUID

import cats.Monad

class LoyaltyPoints[F[_] : Monad : UserRepository : EmailService] {
  def addPoints(userId: UUID, points: Int): F[Either[String, Unit]] =
    for {
      userOpt <- UserRepository[F].findUser(userId)
      result <- userOpt match {
        case None => Monad[F].pure(Left("User not found"))
        case Some(user: User) =>
          val updated = user.copy(loyaltyPoints = user.loyaltyPoints + points)
          UserRepository[F].updateUser(updated)
          EmailService[F].sendEmail(
            user.email,
            "Points Added",
            s"You have now ${updated.loyaltyPoints} loyalty points.")
          Monad[F].pure(Right(()))
      }
    } yield result
}
