package pf.task.waluty.model


final case class UserAccount(name:String,regno:String,balance:Map[String,BigDecimal])
