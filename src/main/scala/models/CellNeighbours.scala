package models

sealed trait NeighbourCoordinate {
  val xCalculation: Int => Int
  val yCalculation: Int => Int
}

private case object UpperLeft extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x - 1
  val yCalculation: Int => Int = x => x - 1
}

private case object UpperMiddle extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x
  val yCalculation: Int => Int = x => x - 1
}

private case object UpperRight extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x + 1
  val yCalculation: Int => Int = x => x - 1
}

private case object Left extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x - 1
  val yCalculation: Int => Int = x => x
}

private case object Right extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x + 1
  val yCalculation: Int => Int = x => x
}

private case object LowerLeft extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x - 1
  val yCalculation: Int => Int = x => x + 1
}

private case object LowerMiddle extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x
  val yCalculation: Int => Int = x => x + 1
}

private case object LowerRight extends NeighbourCoordinate {
  val xCalculation: Int => Int = x => x + 1
  val yCalculation: Int => Int = x => x + 1
}

object CellNeighbours {

  private val allPositions: List[NeighbourCoordinate] = List(
    UpperLeft,
    UpperMiddle,
    UpperRight,
    Left,
    Right,
    LowerLeft,
    LowerMiddle,
    LowerRight
  )

  def getNeighbourCoordinates(cellCoordinate: CellCoordinate): List[CellCoordinate] = {
    allPositions flatMap { position =>
      val x = position.xCalculation(cellCoordinate.x)
      val y = position.yCalculation(cellCoordinate.y)

      if(x < 0 || y < 0) None
      else Some(CellCoordinate(x, y))
    }
  }
}