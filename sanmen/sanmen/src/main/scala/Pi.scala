import scala.collection.mutable.ArrayBuffer

object Pi {

  def getPi1(n: Int): Double = {
    var pi = 2.0
    var t = 2.0
    for (i <- 1 to n) {
      t *= i
      t /= (2 * i + 1)
      pi += t
    }
    pi
  }

  def getPi(n: Int): ArrayBuffer[Int] = {
    val pi = ArrayBuffer[Int]()
    val t = ArrayBuffer[Int]()
    for (i <- 0 until n) {
      pi += (if (i > 0) 0 else 2)
      t += (if (i > 0) 0 else 2)
    }
    var run = true
    var i = 1
    while (run && i < 200000000) {
      //t *= i
      var tmp = 0
      for (j <- n - 1 to 0 by -1) {
        tmp = tmp + t(j) * i
        t(j) = tmp % 10
        tmp /= 10
      }
      //t /= (2*i+1)
      tmp = 0
      for (j <- 0 until n) {
        tmp = tmp * 10 + t(j)
        t(j) = tmp / (2 * i + 1)
        tmp = tmp % (2 * i + 1)
      }
      //pi += t
      tmp = 0
      run = false
      for (j <- n - 1 to 0 by -1) {
        tmp = pi(j) + t(j) + tmp
        pi(j) = tmp % 10
        tmp = tmp / 10
        if (t(j) > 0)
          run = true
      }
      i += 1
    }
    pi
  }

  //(n+1)!/e > 10^m即可
  //lg3+lg5/2+……+lg(2n+1)/n>x+1即可
  //3+5/2+……+(2n+1)/n>10^(x+1)即可
  def getLength(m: Int): Int = {
    var r = 2
    var tmp = 0.0
    var n = 1
    while (tmp < (m + 1)) {
      tmp += math.log10((2 * n + 1) / n)
      n += 1
    }
    n
  }

  def getPi2(n: Int): Double = {
    var pi = 0.0
    for (i <- n to 1 by -1) {
      pi = 1.0 + pi * i / (2 * i + 1)
    }
    pi * 2
  }

  def dealPi(x: Int): ArrayBuffer[Int] = {
    val more = 5
    val a = ArrayBuffer[Int]()
    val n = getLength(x)
    println(s"for $x, length = $n")
    for (i <- 0 to x + more)
      a += 0
    var c = 0
    for (j <- n until 0 by -1) {
      //pi = pi/(2*i+1)
      val d = 2 * j + 1
      for (i <- 0 until x + more) {
        a(i) = c / d
        c = (c % d) * 10 + a(i + 1)
      }
      a(x + more) = c / d
      //pi = pi * i
      var b = 0
      for (i <- x + more to 0 by -1) {
        a(i) = a(i) * j + b
        b = a(i) / 10
        a(i) %= 10
      }
      //pi = pi + 1
      a(0) += 1
      c = a(0)
    }
    var t = 0
    for (i <- x + more to 0 by -1) {
      a(i) = a(i) * 2 + t
      t = a(i) / 10
      a(i) = a(i) % 10
    }
    a
  }

  def pnt(a: ArrayBuffer[Int]): Unit = {
    println(s"Pi = ${a.head}.")
    val b = a.tail
    for (i <- b.indices) {
      print(s"${b(i)}")
      if (i > 0) {
        if ((i + 1) % 20 == 0) {
          println()
        }
        else if ((i + 1) % 5 == 0) {
          print(s"\t")
        }
      }
    }
    println()
  }

  def main(args: Array[String]): Unit = {
    val s = System.currentTimeMillis()
    val n = 200
    val Pi1 = getPi1(n)
    println(s"pi = $Pi1")

    /*val m = 200
    val pi = getPi(m)
    println(s"pi length = ${pi.length}")
    println(s"pi = ${pi.head}.${pi.tail.mkString("")}")*/

    val pi2 = getPi2(n)
    println(s"pi2 = $pi2")

    /*val l = getLength(n)
    println(s"length of $n: $l")*/

    pnt(dealPi(n))
    println(s"end, time cost: ${System.currentTimeMillis() - s} ms!")
  }
}
