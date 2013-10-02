package words

import java.io.File


object CountWords extends App {
  if(args.length != 1) {
    throw new IllegalArgumentException("Required input: path to text file.")
  }

  val wordCounter = WordCounter(new File(args(0)))
  wordCounter.listTop25Words.foreach(println)

}
