package pf.task.waluty.routes

import pf.task.waluty.repositories.AccountRepository
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import cats.Monad
import cats.Defer
import org.http4s.implicits._

final class AccountRoutes[F[_]: Monad: Defer](accountRepo: AccountRepository[F])
    extends Http4sDsl[F] {

  private[this] val prefixPath = "/account/v1/"

  val httpRoutes: HttpRoutes[F] = HttpRoutes.of {
    case GET -> Root =>
      Ok()
  }

    val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
