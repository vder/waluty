package pf.task.waluty.routes

import pf.task.waluty.repositories.AccountRepository
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import cats.Monad
import cats.Defer
import org.http4s.implicits._
import org.http4s.circe._
import cats.FlatMap
import cats.implicits._
import io.circe._
import io.circe.syntax._
import pf.task.waluty.model.UserAccount
import pf.task.waluty.TypeAliases._
import cats.effect.Sync

final class AccountRoutes[F[_]: Monad: Defer: FlatMap: MonadThrowable: Sync](
    accountRepo: AccountRepository[F]
) {

  private[this] val prefixPath = "/api/v1/accounts"
  implicit val userDecoder = jsonOf[F, UserAccount]

  val httpRoutes: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of {
      case GET -> Root =>
        for {
          allAccounts <- accountRepo.findAll
          response <- Ok(allAccounts.asJson)
        } yield response
      case GET -> Root / regno =>
        for {
          account <- accountRepo.getAccount(regno)
          response <- Ok(account.asJson)
        } yield response
      case req @ PUT -> Root / regno =>
        for {
          userAccount <-  req.as[UserAccount]
          account <- accountRepo.updateAccount(userAccount.copy(regno = regno))
          response <- Ok(account.asJson)
        } yield response
      case DELETE -> Root / regno =>
        for {
          account <- accountRepo.deleteAccount(regno)
          response <- Ok(account.asJson)
        } yield response    
      case req @ POST -> Root =>
        for {
          userAccount <-  req.as[UserAccount]
          account <- accountRepo.createAccount(userAccount)
          response <- Ok(account.asJson)
        } yield response       
  }
}

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}

object AccountRoutes {
  def make[F[_]:Monad :Sync]( accountRepo: AccountRepository[F]) = Sync[F].delay{new AccountRoutes[F]( accountRepo: AccountRepository[F])}
}