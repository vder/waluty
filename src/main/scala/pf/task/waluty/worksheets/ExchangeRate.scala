package pf.task.waluty.worksheets

import io.circe.Decoder
import io.circe.HCursor

final case class ExchangeRate(fromCurrency:String,rate:BigDecimal)

object ExchangeRate{
    implicit val ExchangeRateDecoder = new Decoder[ExchangeRate]{

      override def apply(c: HCursor): Decoder.Result[ExchangeRate] = 
      for {
          code <- c.get[String]("code")
          rate <- c.downField("rates").downArray.downField("mid").as[BigDecimal]
      } yield ExchangeRate(code,rate)

    }
}