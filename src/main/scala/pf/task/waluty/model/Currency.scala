package pf.task.waluty.model

import scala.language.implicitConversions
import io.circe.Decoder
import io.circe.Encoder
import io.circe._, io.circe.generic.semiauto._

sealed trait CurrencyCode

final case object USD extends CurrencyCode
final case object PLN extends CurrencyCode

object CurrencyCode {
    self =>
  def apply(codeStr: String) = codeStr match{
      case "USD" => USD
       case "PLN" => PLN 
  }

  implicit val currencyCodeEncoder: Encoder[CurrencyCode] =
    new Encoder[CurrencyCode] {
      final def apply(a: CurrencyCode): Json = a match {
        case PLN => Json.fromString("PLN")
        case USD => Json.fromString("USD")
      }
    }
  implicit val currencyCodeDecoder: Decoder[CurrencyCode] =
    new Decoder[CurrencyCode] {
      final def apply(c: HCursor): Decoder.Result[CurrencyCode] =
        for {
          currCode <- c.downField("code").as[String]
        } yield self.apply(currCode) 
    }
}

case class Currency(code: CurrencyCode, amount: BigDecimal)

object Currency {

  def apply(code: String, amount: BigDecimal): Currency = Currency(CurrencyCode(code), amount)

  def currency2Tuple(curr: Currency): (String, BigDecimal) =
    curr.code match {
      case USD => ("USD", curr.amount)
      case PLN => ("PLN", curr.amount)
    }

  implicit val currencyDecoder: Decoder[Currency] = deriveDecoder[Currency]
  implicit val currencyEncoder: Encoder[Currency] = deriveEncoder[Currency]

}
