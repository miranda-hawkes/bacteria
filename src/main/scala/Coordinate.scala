case class Coordinate(x: Int, y: Int)

object Coordinate {
  def apply(input: String): Coordinate = cleanseInput(input.split(","))

  private def cleanseInput(input: Array[String]): Coordinate = {
    require(input.length == 2, "Only two comma-separated values allowed")

    try {
      new Coordinate(input.head.toInt, input.tail.head.toInt)
    } catch {
      case ex: NumberFormatException => throw new IllegalArgumentException("Can only accept two comma separated digits")
    }
  }

  def unapply(coordinate: Coordinate): String = s"${coordinate.x},${coordinate.y}"
}