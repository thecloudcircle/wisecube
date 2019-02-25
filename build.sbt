name := "dstlr"

version := "0.1"

scalaVersion := "2.11.6"

resolvers += "Restlet Repository" at "http://maven.restlet.org"

// https://mvnrepository.com/artifact/com.lucidworks.spark/spark-solr
//libraryDependencies += "com.lucidworks.spark" % "spark-solr" % "3.6.0"

// https://mvnrepository.com/artifact/edu.stanford.nlp/stanford-corenlp
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2" classifier "models-english" classifier "models-english-kbp"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
//libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"

// https://mvnrepository.com/artifact/org.neo4j.driver/neo4j-java-driver
libraryDependencies += "org.neo4j.driver" % "neo4j-java-driver" % "1.7.2"

// https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"