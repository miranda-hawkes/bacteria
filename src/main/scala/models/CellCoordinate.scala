package models

case class CellCoordinate(x: Int, y: Int)

object CellCoordinate {
  def apply(input: String): CellCoordinate = cleanseInput(input.split(","))

  private def cleanseInput(input: Array[String]): CellCoordinate = {
    if(input.length != 2) throw new IllegalArgumentException("Input should contain one comma")
    else
      try {
        new CellCoordinate(input.head.trim.toInt, input.tail.head.trim.toInt)
      } catch {
        case ex: NumberFormatException => throw new IllegalArgumentException("Input contains non-digits")
      }
  }

  def unapply(coordinate: CellCoordinate): String = s"${coordinate.x} , ${coordinate.y}"
}