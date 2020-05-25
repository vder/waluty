package pf.task.waluty.model

sealed trait Currency 
 final case object USD extends Currency
 final case object PLN extends Currency

