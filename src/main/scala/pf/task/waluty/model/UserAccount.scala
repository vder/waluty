package pf.task.waluty.model

import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.semiauto._

final case class UserAccount(name:String,regno:String,balance:List[Currency])


object UserAccount{
implicit val userAccountDecoder: Decoder[UserAccount] = deriveDecoder[UserAccount]
implicit val userAccountEncoder: Encoder[UserAccount] = deriveEncoder[UserAccount]

}
