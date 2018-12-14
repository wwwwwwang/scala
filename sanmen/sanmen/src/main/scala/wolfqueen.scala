import scala.collection.mutable.ArrayBuffer

object wolfqueen {
  val LENGTH = 8
  var ans: ArrayBuffer[Int] = ArrayBuffer[Int]()
  var answerCnt = 0

  def pnt(): Unit = {
    print(s"##one answer:")
    for (i <- ans.indices) {
      print(s"${i + 1}->${ans(i)} ")
    }
    println()
  }

  def check(): Boolean = {
    var ret = true
    if (ans.size > 1) {
      val now = ans.size - 1
      val v = ans.last
      for (i <- 0 until now) {
        if (ans(i) == v || now - i == math.abs(v - ans(i)))
          ret = false
      }
    }
    ret
  }

  def deal(n: Int): Unit = {
    if (n == LENGTH) {
      pnt()
      answerCnt += 1
    } else {
      for (i <- 0 until LENGTH) {
        ans += i + 1
        if (check())
          deal(n + 1)
        ans = ans.dropRight(1)
      }
    }
  }

  def check1(v: Int): Boolean = {
    var ret = true
    if (ans.nonEmpty) {
      val now = ans.size
      for (i <- 0 until now) {
        if (ans(i) == v + 1 || now - i == math.abs(v + 1 - ans(i)))
          ret = false
      }
    }
    ret
  }

  def deal1(n: Int): Unit = {
    if (n == LENGTH) {
      pnt()
      answerCnt += 1
    } else {
      for (i <- 0 until LENGTH) {
        if (check1(i)) {
          ans += i + 1
          deal1(n + 1)
          ans = ans.dropRight(1)
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val s = System.currentTimeMillis()
    deal1(0)
    println(s"there are $answerCnt answer! Time used:${System.currentTimeMillis() - s} ms!")
  }
}
