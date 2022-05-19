package models

sealed trait CellNeighbourCoordinates {
  val xCalculation: Int => Int
  val yCalculation: Int => Int
}

private case object UpperLeft extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x - 1
  val yCalculation: Int => Int = x => x - 1
}

private case object UpperMiddle extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x
  val yCalculation: Int => Int = x => x - 1
}

private case object UpperRight extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x + 1
  val yCalculation: Int => Int = x => x - 1
}

private case object Left extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x - 1
  val yCalculation: Int => Int = x => x
}

private case object Right extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x + 1
  val yCalculation: Int => Int = x => x
}

private case object LowerLeft extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x - 1
  val yCalculation: Int => Int = x => x + 1
}

private case object LowerMiddle extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x
  val yCalculation: Int => Int = x => x + 1
}

private case object LowerRight extends CellNeighbourCoordinates {
  val xCalculation: Int => Int = x => x + 1
  val yCalculation: Int => Int = x => x + 1
}

case class CellNeighbours(alive: List[CellCoordinate], dead: List[CellCoordinate])

object CellNeighbours {

  private val allPositions: List[CellNeighbourCoordinates] = List(
    UpperLeft,
    UpperMiddle,
    UpperRight,
    Left,
    Right,
    LowerLeft,
    LowerMiddle,
    LowerRight
  )

  def getNeighbourCoordinatesForCell(cellCoordinate: CellCoordinate): List[CellCoordinate] = {
    allPositions flatMap { position =>
      val x = position.xCalculation(cellCoordinate.x)
      val y = position.yCalculation(cellCoordinate.y)

      if(x < 0 || y < 0) None
      else Some(CellCoordinate(x, y))
    }
  }

  def getAliveAndDeadNeighbours(bacteriaCell: CellCoordinate,
                                petriDish: PetriDish): CellNeighbours = {
    val neighbours = CellNeighbours.getNeighbourCoordinatesForCell(bacteriaCell)
    val (alive, dead) = neighbours.partition { neighbour =>
      petriDish.liveBacteria.contains(neighbour)
    }
    CellNeighbours(alive, dead)
  }
}