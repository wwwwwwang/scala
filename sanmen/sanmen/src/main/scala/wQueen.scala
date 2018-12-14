object wQueen {
  val LENGTH = 8
  var ans = new Array[Int](LENGTH)
  var answerCnt = 0

  def pnt(): Unit = {
    print(s"##one answer:")
    for (i <- ans.indices) {
      print(s"${i + 1}->${ans(i)} ")
    }
    println()
  }

  def check1(n: Int, v: Int): Boolean = {
    var ret = true
    for (i <- 0 until n) {
      if (ans(i) == v + 1 || n - i == math.abs(v + 1 - ans(i)))
        ret = false
    }
    ret
  }

  def deal1(n: Int): Unit = {
    if (n == LENGTH) {
      pnt()
      answerCnt += 1
    } else {
      for (i <- 0 until LENGTH) {
        if (check1(n,i)) {
          ans(n) = i+1
          deal1(n + 1)
          //ans(n) = 0
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
