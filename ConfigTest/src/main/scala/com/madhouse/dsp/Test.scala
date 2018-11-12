package com.madhouse.dsp

import com.madhouse.dsp.Utils.ConfigReader._
import org.apache.commons.logging.{Log, LogFactory}

/**
  * Created by Madhouse on 2017/9/14.
  */
object Test {
  val log: Log = LogFactory.getLog(Test.getClass)
  def main(args: Array[String]): Unit = {
    //ConfigReader.init("application.conf","app")
    //ConfigReader.init("hdfs:///madplatform/application.conf","app")
    /*val sparkAppName: String = ConfigReader.getWithElse("spark.app_name", "AdmTAReport")
    val outputPath: String = ConfigReader.getWithElse("kafka.output_path", "/madplatform/analytics/admta")
    val mysqlUrl: String = ConfigReader.getWithElse("mysql.url","jdbc:mysql://localhost:3306/mahad")
    val mysqlUser: String = ConfigReader.getWithElse("mysql.user", "root")
    val mysqlPasswd: String = ConfigReader.getWithElse("mysql.passwd", "123456")
    val mysqlTable: String = ConfigReader.getWithElse("mysql.table", "md_dmp_ta_report")*/
    log.info(
      s"""#####
        | sparkAppName = $sparkAppName,
        | outputPath = $outputPath,
        | mysqlUrl = $mysqlUrl,
        | mysqlUser = $mysqlUser,
        | mysqlPasswd = $mysqlPasswd,
        | mysqlTable = $mysqlTable
      """.stripMargin)
  }
}
