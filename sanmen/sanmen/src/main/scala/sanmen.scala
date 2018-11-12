import scala.util.Random
import scala.util.control.Breaks._

object sanmen {
  def fun(right: Int, known: Boolean, exchange: Boolean): Int = {
    var res = 0
    var myChosen = Random.nextInt(3)
    var hisChosen = (myChosen + 1) % 3
    if (known) {
      if (hisChosen == right) {
        hisChosen = (hisChosen + 1) % 3
      }
    }
    val other = 3 - myChosen - hisChosen
    //println(s"before exchange myChosen = $myChosen")
    if (exchange) {
      myChosen = other
    }
    if (myChosen == right) {
      res = 1
    }
    if (hisChosen == right) {
      res = -1
    }
    //println(s"right=$right,mychosen=$myChosen,hischosen=$hisChosen,other=$other,res=$res")
    res
  }

  def getRes(totaltimes:Int, known: Boolean, exchange: Boolean):Unit={
    val right = Random.nextInt(3)
    var cnt = 0
    var res = 0
    while (cnt < totaltimes) {
      breakable {
        val r = fun(right, known, exchange)
        if (r >= 0) {
          cnt += 1
          res += r
        } else break()
      }
    }
    println(s"when known is $known and exchange is $exchange, total test times is $totaltimes, you get the gift $res times")
  }

  def main(args: Array[String]): Unit = {
    val totaltimes = 100000000
    getRes(totaltimes, known = true, exchange = true)
    getRes(totaltimes, known = true, exchange = false)
    getRes(totaltimes, known = false, exchange = true)
    getRes(totaltimes, known = false, exchange = false)
  }
}
