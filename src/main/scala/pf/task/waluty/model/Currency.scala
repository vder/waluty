package pf.task.waluty.model


import scala.language.implicitConversions

sealed trait CurrencyCode

 final case object USD extends CurrencyCode
 final case object PLN extends CurrencyCode


 case class Currency(code:CurrencyCode, amount: BigDecimal)


 object Currency{

     def apply(code:String,amount:BigDecimal):Currency= code match {
         case "USD" => Currency(USD,amount)
         case "PLN" => Currency(PLN,amount)
         case _ => Currency(PLN,0)
      }

    def currency2Tuple(curr:Currency):(String,BigDecimal)=
    curr.code match{
        case USD=> ("USD",curr.amount)
        case PLN=> ("PLN",curr.amount)
    }  
 }
