package pf.task.waluty.services

import scala.xml.Elem
import pf.task.waluty.worksheets.ExchangeRate
import io.circe.Json
import io.circe.syntax._
import org.http4s.scalaxml._
import pf.task.waluty.TypeAliases._

import cats.effect.Timer
import cats.effect.ContextShift
import cats.effect.IO
import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.circe._
import org.http4s.Uri
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
import pf.task.waluty.model.Currency
import scala.language.implicitConversions
import cats.ApplicativeError
import cats.FlatMap
import cats.Functor
import cats.effect.Sync

trait ExchangeService[F[_]] {
  def calculate(
      fromCurrencyCode: String,
      toCurrencyCide: String,
      amount: BigDecimal
  ): F[BigDecimal]
}

class LiveExchangeService[F[_]: ApplicativeThrowable: FlatMap: Functor: Sync](
    client: Client[F]
) extends ExchangeService[F] {

  val mc = new java.math.MathContext(2)

  override def calculate(
      fromCurrencyCode: String,
      toCurrencyCode: String,
      amount: BigDecimal
  ): F[BigDecimal] =
    for {
      uri <- Uri
        .fromString(
          s"http://api.nbp.pl/api/exchangerates/rates/a/${toCurrencyCode.toLowerCase()}"
        )
        .liftTo[F]
      respJson <- client.expect[Json](uri)
      exRate <- respJson.as[ExchangeRate].liftTo[F]
    } yield (amount / exRate.rate).round(mc)

}

object LiveExchangeService {
  def make[F[_]: ApplicativeThrowable: FlatMap: Functor: Sync](
      client: Client[F]
  ) = new LiveExchangeService[F](client)
}
