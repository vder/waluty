package pf.task.waluty.model


import scala.language.implicitConversions

sealed trait Currency {
    def amount: BigDecimal
}
 final case class USD(amount:BigDecimal) extends Currency
 final case class PLN(amount:BigDecimal) extends Currency


 object Currency{

     def apply(code:String,amount:BigDecimal)= code match {
         case "USD" => USD(amount)
         case "PLN" => PLN(amount)
         case _ => PLN(0)
      }

    def currency2Tuple(curr:Currency):(String,BigDecimal)=
    curr match{
        case USD(amt)=> ("USD",amt)
        case PLN(amt)=> ("PLN",amt)
    }  
 }
