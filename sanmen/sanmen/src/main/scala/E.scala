import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

object E {
  def getE1(n: Int): Double = {
    var e = 1.0
    var t = 1.0
    for (i <- 1 to n) {
      t = t / i
      e = e + t
    }
    e
  }

  def getE2(n: Int): Double = {
    var e = 0.0
    for (i <- n to 1 by -1) {
      e = (e + 1) / i
    }
    e + 1
  }

  //(n+1)!/e > 10^10000即可
  //(n+1)!/e > 10^m即可
  def getLength(m: Int): Int = {
    var r = 2
    var tmp = 1 / 2.718
    var cnt = m
    while (cnt > 0) {
      tmp *= r
      while (tmp > 10) {
        tmp /= 10
        cnt -= 1
      }
      r += 1
    }
    r - 2
  }

  def toString(i: Int, n: Int): String = {
    n match {
      case 2 => f"$i%02d"
      case 3 => f"$i%03d"
      case 4 => f"$i%04d"
      case 5 => f"$i%05d"
      case 6 => f"$i%06d"
      case 7 => f"$i%07d"
      case 8 => f"$i%08d"
      case 9 => f"$i%09d"
      case 10 => f"$i%010d"
      case _ => i.toString
    }
  }

  def dealE(m: Int, n: Int, base: Int = 1000): ArrayBuffer[String] = {
    var remainder: ArrayBuffer[Int] = ArrayBuffer[Int]()
    for (i <- 0 to n) {
      remainder += 1
    }
    val ret: ArrayBuffer[String] = ArrayBuffer[String]()
    val dealOneTime = math.log10(base).toInt
    for (i <- m until 0 by (-dealOneTime)) {
      var pe = 0
      for (j <- n to 1 by -1) {
        pe = pe + remainder(j) * base
        remainder(j) = pe % j
        pe = pe / j
      }
      if (pe > base)
        pe = pe % base
      ret += toString(pe, dealOneTime)
    }
    ret
  }

  def isPrime(n: Int): Boolean = {
    var ret = true
    breakable(
      for (i <- 2 to math.sqrt(n).toInt) {
        if (n % i == 0) {
          ret = false
          break()
        }
      }
    )
    ret
  }

  def findPrime(n: Int): ArrayBuffer[Int] = {
    val ret: ArrayBuffer[Int] = ArrayBuffer[Int]()
    for (i <- 2 to n) {
      if (isPrime(i))
        ret += i
    }
    ret
  }

  def main(args: Array[String]): Unit = {
    /*val e = math.E
    println(s"e=$e")
    val e1 = getE1(30)
    println(f"getE1 e = $e1%.20f")
    val e2 = getE2(30)
    println(f"getE2 e = $e2%.20f")*/

    val s = System.currentTimeMillis()
    val m = 1000
    val l = getLength(m)
    val r = dealE(m, l, 100000)
    println(s"m = $m, n = $l, r.size = ${r.size}")
    print(s"e = 2.")
    /*for (i <- r.indices) {
      if (i % 5 == 0)
        println()
      print(s"${r(i)}\t")
    }*/

    val eString = r.mkString("")
    println(s"$eString")
    println(s"eString.length = ${eString.length}")

    val max = 1000000
    val primeList = findPrime(max)
    //println(s"prime list small than $max: ${primeList.mkString(", ")}")

    var cnt = 0
    for (i <- 0 until eString.length - 10) {
      val tmp = eString.substring(i, i + 10)
      var isPrime = true
      for (j <- primeList.indices) {
        breakable(
          if (tmp.startsWith("0") || tmp.toLong % primeList(j) == 0) {
            isPrime = false
            break()
          }
        )
      }
      if (isPrime) {
        cnt += 1
        println(s"find prime $cnt, in position: $i, value is: $tmp")
      }
    }

    println(s"\ntime used: ${System.currentTimeMillis() - s} ms!")
  }
}
