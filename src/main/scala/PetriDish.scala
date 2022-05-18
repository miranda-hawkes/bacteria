case class PetriDish(bacteria: List[CellCoordinate])

object PetriDish {
  def unapply(petriDish: PetriDish): String = {
    petriDish.bacteria.map(cell => CellCoordinate.unapply(cell)).mkString("\n")
  }
}

