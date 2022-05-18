case class PetriDish(bacteria: List[CellCoordinate])

object PetriDish {
  // cellCoordinates: => List[String] is some trickery to get past lack of generic support and JVM compilation error with this and line 1
  def apply(cellCoordinates: => List[String]): PetriDish = {
    new PetriDish(
      cellCoordinates.map(stringValue => CellCoordinate(stringValue))
    )
  }

  def unapply(petriDish: PetriDish): List[String] = {
    petriDish.bacteria.map(cell => s"${CellCoordinate.unapply(cell)}\n")
  }
}

