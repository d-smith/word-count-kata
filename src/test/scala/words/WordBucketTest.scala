package words

import org.scalatest.{PrivateMethodTester, FunSuite}
import java.io.File


class WordBucketTest extends FunSuite with PrivateMethodTester {

  test("A line can be bucketed") {


    val wordCounter = WordCounter("B B A C C C D D D D E")
    val decoratedBucketWords = PrivateMethod[Map[String,Int]]('bucketWords)
    val bucket = wordCounter invokePrivate decoratedBucketWords()


    assert(1 === bucket("A"))
    assert(2 === bucket("B"))
    assert(3 === bucket("C"))
    assert(4 === bucket("D"))
    assert(1 === bucket("E"))
  }

  test("A bucket can be sorted") {
    val wordCounter = WordCounter("B B A C C C D D D D E")
    val decoratedBucketWords = PrivateMethod[Map[String,Int]]('bucketWords)
    val bucket = wordCounter invokePrivate decoratedBucketWords()

    val decoratedSortBucket = PrivateMethod[collection.mutable.SortedSet[(String,Int)]]('sortBucket)
    val sorted:collection.mutable.SortedSet[(String,Int)] = wordCounter invokePrivate decoratedSortBucket(bucket)
    val (word,count) = sorted.head
    assert("D" === word)
    assert(4 === count)
  }

  test("war and peace can be bucketed") {
    val wordCounter = WordCounter(new File("./src/test/resources/war_and_peace.txt"))
    val decoratedBucketWords = PrivateMethod[Map[String,Int]]('bucketWords)
    val bucket = wordCounter invokePrivate decoratedBucketWords()
    assert(true === (bucket.size > 0))
  }

  test("top 25 might have fewer than 25") {
    val wordCounter = WordCounter(new File("./src/test/resources/alphabet.txt"))
    val topWhatever = wordCounter.listTop25Words
    assert("J" === topWhatever.head._1)
  }

  test("top 25 build from stream can be called more than once") {
    val wordCounter = WordCounter(new File("./src/test/resources/alphabet.txt"))
    wordCounter.listTop25Words
    val topWhatever = wordCounter.listTop25Words
    assert("J" === topWhatever.head._1)
  }


}
