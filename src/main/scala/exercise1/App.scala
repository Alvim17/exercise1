package exercise1

trait App {

  def pipeline(): Unit = {}

  final def main(args: Array[String]): Unit = {
    try {
      //TODO: loggin
      pipeline()
      //TODO: loggin
    }
    catch {
      case e: Exception =>
        //TODO: logging
        throw e
    }
  }

}
