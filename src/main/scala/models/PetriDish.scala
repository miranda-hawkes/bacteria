package models

case class PetriDish(liveBacteria: List[CellCoordinate])

object PetriDish {
  def unapply(petriDish: PetriDish): String = {
    petriDish.liveBacteria.map(cell => CellCoordinate.unapply(cell)).mkString("\n")
  }
}

