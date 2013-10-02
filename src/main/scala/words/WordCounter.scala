package words

import java.io.File
import scala.io.Source


class WordCounter private (wordSource: Source) {
  var top25Words: Option[Array[(String, Int)]] = None

  private def bucketWords() : Map[String,Int] = {
    val counts = scala.collection.mutable.Map[String, Int]().withDefault(x => 0)
    for(word <- wordSource.getLines.flatMap(x => x.split("\\s+")))
      counts(word) += 1

    counts.toMap
  }

  private def sortBucket(count: Map[String, Int]) : collection.mutable.SortedSet[(String,Int)] = {
    def WordCountTupleOrdering = new Ordering[(String,Int)] {
      def compare(a: (String,Int), b: (String, Int)) = b._2.compare(a._2)
    }

    val sortedSet = collection.mutable.SortedSet[(String,Int)]()(WordCountTupleOrdering)
    count.foreach(x => sortedSet += x)
    sortedSet
  }

  def listTop25Words: Array[(String,Int)] = {
    top25Words match {
      case Some(set) => set
      case None => {
        val sortedArray = sortBucket(bucketWords).slice(0,50).toArray
        top25Words = Some(sortedArray)
        sortedArray
      }
    }
  }
}


object WordCounter {
  def apply(lines: String) : WordCounter = {
    new WordCounter(Source.fromString(lines))
  }

  def apply(file: File) : WordCounter = {
    new WordCounter(Source.fromFile(file))
  }
}
