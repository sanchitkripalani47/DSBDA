Apache Spark Installation on UBUNTU

https://computingforgeeks.com/how-to-install-apache-spark-on-ubuntu-debian/

 sudo apt-get install default-jdk
 java -version
 wget https://dlcdn.apache.org/spark/spark-3.2.1/spark-3.2.1-bin-hadoop3.2.tgz
 tar xvf spark-3.2.1-bin-hadoop3.2.tgz
 sudo mv spark-3.2.1-bin-hadoop3.2/ /opt/spark
 nano  ~/.bashrc
export SPARK_HOME=/opt/spark
   export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin
 source ~/.bashrc



Program

 nc -lk 9999
 spark-shell
import org.apache.spark.sql.functions._
 import org.apache.spark.sql.SparkSession
import spark.implicits._
val spark = SparkSession.builder.appName("StructuredNetworkWordCount").getOrCreate()
val lines = spark.readStream.format("socket").option("host", "localhost").option("port", 9999).load()
 val words = lines.as[String].flatMap(_.split(" "))
 val wordCounts = words.groupBy("value").count()
 val query = wordCounts.writeStream.outputMode("complete").format("console").start()
query.awaitTermination() //Most probably Not be needed