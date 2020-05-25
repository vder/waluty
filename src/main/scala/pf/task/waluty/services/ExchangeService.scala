package pf.task.waluty.services

import pf.task.waluty.model.Currency
import org.http4s.blaze.http.HttpClient

trait ExchangeService[F[_]] {
  def calculate(fromCurrency:Currency, toCurrency:Currency, ammount:BigDecimal) : F[BigDecimal]
}

class LiveExchangeService[F[_]](client: HttpClient) extends ExchangeService[F]{

  override def calculate(fromCurrency: Currency, toCurrency: Currency, ammount: BigDecimal): F[BigDecimal] = ???

    
}
