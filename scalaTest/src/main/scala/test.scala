/**
  * Created by SNOW on 2017/5/19.
  */
object test {
  //@volatile var value = true
  //var value = false

  def run(): Unit ={
    for (i <- 1 to 1000) {
      if (Constant.value) {
        println(s"value = ${Constant.value} when i = $i, time = ${System.currentTimeMillis()}")
        Constant.value = false
        println(s"value = ${Constant.value} has changed to false in main thread! ")
      }else{
        println(s"value = ${Constant.value} in else switch! ")
      }
      Thread sleep 1000
    }
  }

  def main(args: Array[String]): Unit = {
    //new Thread(new ThreadDemo("test")).start()
    for (i <- 1 to 1000) {
      //if (value) {
      println(s"value = ${Constant.value} when i = $i, time = ${System.currentTimeMillis()}")
      Thread.sleep(1000)
      Constant.value = false

      //}
    }
  }
}

class ThreadDemo(threadName: String) extends Runnable {
  override def run() {
    println("starts....")
    Timer.changeValue()
    println("ends...")
  }
}
