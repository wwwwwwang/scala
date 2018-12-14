import scala.collection.mutable.ArrayBuffer

object shudu {

  case class O(r: Int, c: Int, v: Int)

  case class P(r: Int, c: Int, pv: ArrayBuffer[Int])

  val SMALL_LENGTH: Int = 3
  val LENGTH: Int = SMALL_LENGTH * SMALL_LENGTH
  var answerCnt = 0
  var board: Array[Array[Int]] = Array.ofDim(LENGTH, LENGTH)

  def init(): Unit = {
    for (i <- 0 until LENGTH)
      for (j <- 0 until LENGTH)
        board(i)(j) = 0
  }

  def pntRow(s: String, p: String): Unit = {
    print(s"$s")
    for (_ <- 0 until (2 * LENGTH + SMALL_LENGTH * 2 - 1))
      print(s"$p")
    println(s"$s")
  }

  def pnt(): Unit = {
    for (i <- 0 until LENGTH) {
      if (i % SMALL_LENGTH == 0)
        pntRow("|", "-")
      for (j <- 0 until LENGTH) {
        if (j % SMALL_LENGTH == 0)
          print("| ")
        print(s"${board(i)(j)} ")
      }
      println("|")
    }
    pntRow("|", "-")
  }

  def pntPos(p: ArrayBuffer[P]): Unit = {
    for (i <- p.indices) {
      println(s"(${p(i).r},${p(i).c})-->${p(i).pv.mkString(",")}")
    }
  }

  def useInput(in: Array[O]): Unit = {
    for (i <- in.indices) {
      board(in(i).r)(in(i).c) = in(i).v
    }
  }

  def getOnePossible(r: Int, c: Int): ArrayBuffer[Int] = {
    val ret = ArrayBuffer[Int](1, 2, 3, 4, 5, 6, 7, 8, 9)
    //one square
    for (i <- r / SMALL_LENGTH * SMALL_LENGTH until (r / SMALL_LENGTH * SMALL_LENGTH + SMALL_LENGTH))
      for (j <- c / SMALL_LENGTH * SMALL_LENGTH until (c / SMALL_LENGTH * SMALL_LENGTH + SMALL_LENGTH)) {
        ret -= board(i)(j)
        //println(s"row=$i, col=$j, val=${board(i)(j)}")
      }
    //one row
    for (j <- 0 until LENGTH) {
      ret -= board(r)(j)
      //println(s"row=$r, col=$j, val=${board(r)(j)}")
    }

    //one col
    for (i <- 0 until LENGTH) {
      ret -= board(i)(c)
      //println(s"row=$i, col=$c, val=${board(i)(c)}")
    }
    ret
  }

  def getPossible: ArrayBuffer[P] = {
    val ret: ArrayBuffer[P] = ArrayBuffer[P]()
    for (i <- 0 until LENGTH)
      for (j <- 0 until LENGTH) {
        if (board(i)(j) == 0) {
          ret += P(i, j, getOnePossible(i, j))
        }
      }
    ret.sortWith {
      case (a, b) =>
        if (a.pv.size == b.pv.size) {
          if (a.r == b.r) {
            a.c < b.c
          } else {
            a.r < b.r
          }
        } else {
          a.pv.size < b.pv.size
        }
    }
  }

  def usePosOnly(p: ArrayBuffer[P]): ArrayBuffer[P] = {
    var cnt = 0
    for (i <- p.indices) {
      if (p(i).pv.size == 1) {
        board(p(i).r)(p(i).c) = p(i).pv(0)
        cnt += 1
        //println(s"in usePosOnly, set board(${p(i).r},${p(i).c}): 0 -> ${p(i).pv(0)}")
      }
    }
    p.drop(cnt)
  }

  def unUsePos(p: ArrayBuffer[P]): Unit = {
    for (i <- p.indices) {
      if(p(i).pv.size == 1){
        board(p(i).r)(p(i).c) = 0
        //println(s"in unUsePos: board(${p(i).r},${p(i).c}): ${p(i).v} -> 0 ")
      }
    }
  }

  def isOk(r: Int, c: Int, v: Int): Boolean = {
    //println(s"#########isOk($r,$c->$v) in....")
    var ret = false
    for (i <- r / SMALL_LENGTH * SMALL_LENGTH until (r / SMALL_LENGTH * SMALL_LENGTH + SMALL_LENGTH))
      for (j <- c / SMALL_LENGTH * SMALL_LENGTH until (c / SMALL_LENGTH * SMALL_LENGTH + SMALL_LENGTH)) {
        if (board(i)(j) == v) {
          ret = true
          //println(s"#####square: board($i)($j) has $v, when input ($r,$c)->$v")
        }
      }
    if (!ret) {
      for (j <- 0 until LENGTH) {
        if (board(r)(j) == v) {
          ret = true
          //println(s"#####row: board($r)($j) has $v, when input ($r,$c)->$v")
        }
      }
    }
    if (!ret) {
      for (i <- 0 until LENGTH) {
        if (board(i)(c) == v) {
          ret = true
          //println(s"#####col: board($i)($c) has $v, when input ($r,$c)->$v")
        }
      }
    }
    //println(s"#########isOk:($r,$c->$v) out....")
    ret
  }

  def check(): Boolean = {
    var ret = true
    for (i <- 0 until LENGTH) {
      var s = 0
      for (j <- 0 until LENGTH)
        s += board(i)(j)
      if (s != 45)
        ret = false
    }
    for (j <- 0 until LENGTH) {
      var s = 0
      for (i <- 0 until LENGTH)
        s += board(i)(j)
      if (s != 45)
        ret = false
    }
    ret
  }

  def deal(p: ArrayBuffer[P]): Unit = {
    val p1 = usePosOnly(p)
    if (p1.isEmpty) {
      if (check()) {
        pnt()
        answerCnt += 1
      }
    } else {
      for (i <- p1.head.pv.indices) {
        val v = p1.head.pv(i)
        if (!isOk(p1.head.r, p1.head.c, v)) {
          board(p1.head.r)(p1.head.c) = v
          val pos = getPossible
          deal(pos)
          board(p1.head.r)(p1.head.c) = 0
        }
      }
    }
    unUsePos(p)
  }

  def main(args: Array[String]): Unit = {
    init()
    val in1 = Array(O(0, 1, 6), O(0, 3, 5), O(0, 4, 9), O(0, 5, 3),
      O(1, 0, 9), O(1, 2, 1), O(1, 6, 5),
      O(2, 1, 3), O(2, 3, 4), O(2, 7, 9),
      O(3, 0, 1), O(3, 2, 8), O(3, 4, 2), O(3, 8, 4),
      O(4, 0, 4), O(4, 3, 3), O(4, 5, 9), O(4, 8, 1),
      O(5, 0, 2), O(5, 4, 1), O(5, 6, 6), O(5, 8, 9),
      O(6, 1, 8), O(6, 5, 6), O(6, 7, 2),
      O(7, 2, 4), O(7, 6, 8), O(7, 8, 7),
      O(8, 3, 7), O(8, 4, 8), O(8, 5, 5), O(8, 7, 1))

    val in2 = Array(O(0, 0, 8),
      O(1, 2, 3), O(1, 3, 6),
      O(2, 1, 7), O(2, 4, 9), O(2, 6, 2),
      O(3, 1, 5), O(3, 5, 7),
      O(4, 4, 4), O(4, 6, 7),
      O(5, 3, 1), O(5, 5, 5), O(5, 7, 3),
      O(6, 2, 1), O(6, 7, 6), O(6, 8, 8),
      O(7, 2, 8), O(7, 3, 5), O(7, 7, 1),
      O(8, 1, 9), O(8, 6, 4))

    val in3 = Array(O(0, 0, 8),
      O(1, 2, 3), O(1, 3, 6),
      O(2, 1, 7), O(2, 4, 9), O(2, 6, 2),
      O(3, 1, 5), O(3, 5, 7),
      O(4, 4, 4), O(4, 5, 5), O(4, 6, 7),
      O(5, 3, 1), O(5, 7, 3),
      O(6, 2, 1), O(6, 7, 6), O(6, 8, 8),
      O(7, 2, 8), O(7, 3, 5), O(7, 7, 1),
      O(8, 1, 9), O(8, 6, 4))

    val in4 = Array(O(0, 0, 4),O(0, 3, 8),O(0, 7, 3),
      O(1, 0, 5), O(1, 2, 2), O(1, 6, 9), O(1, 8, 4),
      O(2, 1, 7), O(2, 5, 3), O(2, 8, 1),
      O(3, 2, 5), O(3, 4, 8), O(3, 5, 9), O(3, 6, 4),
      O(4, 4, 3), O(4, 7, 2),
      O(5, 3, 2), O(5, 7, 7),
      O(6, 1, 9), O(6, 5, 2),
      O(7, 1, 1), O(7, 4, 5),
      O(8, 2, 6), O(8, 3, 9), O(8, 4, 1), O(8, 6, 8))

    useInput(in4)
    pnt()

    val s = System.currentTimeMillis()
    val p = getPossible

    deal(p)
    println(s"finished, get answer: $answerCnt, time used is ${System.currentTimeMillis() - s} ms!")
  }
}
