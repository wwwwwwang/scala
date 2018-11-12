/**
  * Created by SNOW on 2017/8/1.
  */

import scala.collection.mutable._

object StringUtils {
  def maxSubString(a: String, b: String): ArrayBuffer[String] = {
    val res = ArrayBuffer[String]()
    var maxlength = 0
    for (i <- 0 until a.length) {
      for (j <- 0 until b.length) {
        var ii = i
        var jj = j
        var length = 0
        if (jj < b.length && ii < a.length && b(jj) == a(ii)) {
          //println(s"ii=$ii, jj=$jj, maxlength=$maxlength")
          jj += 1
          ii += 1
          length += 1
          while (jj < b.length && ii < a.length && b(jj) == a(ii)) {
            jj += 1
            ii += 1
            length += 1
          }
          if (length > maxlength) {
            res.clear()
            maxlength = length
            res += b.substring(j, jj)
          } else if (length == maxlength) {
            res += b.substring(j, jj)
          }
        }
      }
    }
    res
  }

  def main(args: Array[String]): Unit = {
    val a = "bbcbaefcfg"
    val b = a.reverse
    val c = maxSubString(a, b)
    for (i <- c) {
      println(s"i am the max subString $i")
    }
  }
}
