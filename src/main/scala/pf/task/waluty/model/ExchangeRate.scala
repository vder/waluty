package pf.task.waluty.worksheets

import io.circe.Decoder
import io.circe.HCursor
import com.lucidchart.open.xtract.{XmlReader, __}
import com.lucidchart.open.xtract.XmlReader._
import com.lucidchart.open.xtract.DefaultXmlReaders
import cats.syntax.all._

final case class ExchangeRate(fromCurrency: String, rate: BigDecimal)

object ExchangeRate {
  implicit val ExchangeRateDecoder = new Decoder[ExchangeRate] {

    override def apply(c: HCursor): Decoder.Result[ExchangeRate] =
      for {
        code <- c.get[String]("code")
        rate <- c.downField("rates").downArray.downField("mid").as[BigDecimal]
      } yield ExchangeRate(code, rate)

  }

  implicit val reader: XmlReader[ExchangeRate] = (
    (__ \ "Code").read[String],
    (__ \ "Rates" \ "Rate" \ "Mid").read[Double]
  ) mapN { case (code, rate) => apply(code, BigDecimal(rate)) }

}
