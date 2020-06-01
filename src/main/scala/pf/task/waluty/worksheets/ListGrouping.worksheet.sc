val z = List(
  ("name1", "pesel1", "USD", 10),
  ("name1", "pesel1", "PLN", 100),
  ("name2", "pesel2", "USD", 20),
  ("name2", "pesel2", "PLN", 200)
)

val ret = z.groupBy { case (name, pesel, _, _) => (name, pesel) }
  .view
  .mapValues(_.map { case (_, _, currency, amount) => (currency, amount) })
  .map { case (x, y) => (x._1, x._2, y) }
  .toList

val ret2= z.groupMap{ case (name, pesel, _, _) => (name, pesel) }{ case (_, _, currency, amount) => (currency, amount) }
           .toList
           .map{case ((name,pesel),list) => (name,pesel,list)}

  println(ret)
  println(ret2)