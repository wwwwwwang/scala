package com.madhouse.dsp.Utils

import java.io.File
import java.net.URI

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.commons.logging.{Log, LogFactory}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataInputStream, FileSystem, Path}

/**
  * Created by Madhouse on 2017/9/14.
  */
object ConfigReader extends java.io.Serializable {
  var config: Config = _
  val log: Log = LogFactory.getLog(ConfigReader.getClass)

  val defaultHdfsPath = "/madplatform/config/"
  var path: String = "application.conf"
  var rootName: String = "app"

  def inputStream2String(is: FSDataInputStream): String = {
    scala.io.Source.fromInputStream(is).getLines().mkString("\n")
  }

  def init(configName: String, rootName: String) {
      /*val fpath =System.getProperty("user.dir")
      log.info(s"#####fpath = $fpath")*/
      val directory = new File("..")
      val filePath = directory.getAbsolutePath
      //log.info(s"#####directory.getAbsolutePath = $filePath")
      val localPath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + configName
      log.info(s"#####path = $localPath")
      val configFile = new File(localPath)
      if (configFile.exists()) {
        config = ConfigFactory.parseFile(configFile).getConfig(rootName)
      } else {
        log.info(s"####Property file not found:$localPath, try to get it from hdfs...")

        val hdfsPath = defaultHdfsPath + "/" + configName
        log.info(s"#####start to read config($hdfsPath) file from hdfs")
        val conf: Configuration = new Configuration
        conf.setBoolean("fs.hdfs.impl.disable.cache", true)
        val fs = FileSystem.get(URI.create(hdfsPath), conf)
        if (fs.exists(new Path(hdfsPath))) {
          val in = fs.open(new Path(hdfsPath))
          /*val str = inputStream2String(in)
          log.info(s"#####string = $str")*/
          config = ConfigFactory.parseString(inputStream2String(in)).getConfig(rootName)
          in.close()
          fs.close()
        } else {
          log.info(s"####$hdfsPath in hdfs is not exist, cannot get config and exit...")
          fs.close()
          sys.exit(1)
        }
      }
  }

  def getWithElse[T](path: String, defaultValue: T): T = {
    if (config.hasPath(path)) {
      defaultValue match {
        case _: Int => config.getInt(path).asInstanceOf[T]
        case _: String => config.getString(path).asInstanceOf[T]
        case _: Double => config.getDouble(path).asInstanceOf[T]
        case _: Long => config.getLong(path).asInstanceOf[T]
        case _: Boolean => config.getBoolean(path).asInstanceOf[T]
        case _ => defaultValue
      }
    } else {
      defaultValue
    }
  }

  val configDefault = init(path,rootName)

  val sparkAppName: String  = getWithElse("spark.app_name", "AdmTAReport")
  val outputPath: String    = getWithElse("kafka.output_path", "/madplatform/analytics/admta")
  val mysqlUrl: String      = getWithElse("mysql.url","jdbc:mysql://localhost:3306/mahad")
  val mysqlUser: String     = getWithElse("mysql.user", "root")
  val mysqlPasswd: String   = getWithElse("mysql.passwd", "123456")
  val mysqlTable: String    = getWithElse("mysql.table", "md_dmp_ta_report")

}
