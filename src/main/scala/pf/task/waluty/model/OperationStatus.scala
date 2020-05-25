package pf.task.waluty.model

trait OperationStatus
final case object OkStatus extends OperationStatus
final case class InvalidInput(msg:String) extends OperationStatus
