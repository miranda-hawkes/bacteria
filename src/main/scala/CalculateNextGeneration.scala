import models.{CellCoordinate, CellNeighbours, PetriDish}
import models.CellNeighbours._

class CalculateNextGeneration(petriDish: PetriDish) {

  def nextGeneration: PetriDish = {

    val aliveCellsWithDeadNeighbours: Map[CellCoordinate, CellNeighbours] = (
      for {
        bacteriaCell <- petriDish.liveBacteria
        cellNeighbours = getAliveAndDeadNeighbours(bacteriaCell, petriDish)
      } yield (bacteriaCell, cellNeighbours)
    ).toMap

    val deadCellsToBeReproducedOnto = calculateReproductionOntoDeadCells(
      aliveCellsWithDeadNeighbours
        .values
        .toList
        .flatMap(cellNeighbours => cellNeighbours.dead)
    )
    val aliveCellsToStayAlive = calculateLiveCellsToStayAlive(aliveCellsWithDeadNeighbours)

    PetriDish(
      deadCellsToBeReproducedOnto
        .toSet
        .union(aliveCellsToStayAlive.toSet)
        .toList
        .sorted
    )
  }

  // Rules 1, 2 & 3
  private def aliveCellWillLiveToNextGeneration(liveNeighbours: List[CellCoordinate]): Boolean = {
    liveNeighbours.size match {
      case 0 | 1 => false
      case 2 | 3 => true
      case _ => false
    }
  }

  // Rule 4
  private def deadCellWillBeReproducedOnto(liveNeighbours: List[CellCoordinate]): Boolean =
    liveNeighbours.size == 3

  // Rules 1, 2 & 3
  def calculateLiveCellsToStayAlive(aliveCellsWithDeadNeighbours: Map[CellCoordinate, CellNeighbours]): List[CellCoordinate] = {
    val aliveCellsAndLiveNeighbours = aliveCellsWithDeadNeighbours map {
      case (aliveCell, cellNeighbours) => (aliveCell, cellNeighbours.alive)
    }

    aliveCellsAndLiveNeighbours
      .filter { case (_, liveNeighbours) => aliveCellWillLiveToNextGeneration(liveNeighbours) }
      .keySet
      .toList
  }

  // Rule 4
  // 'all dead neighbour cells' = for all alive cells in dish, find their immediate neighbours and pick out dead ones
  // this solves the issue of an infinitely sized petri dish / no size needing to be input at start
  def calculateReproductionOntoDeadCells(allDeadNeighbourCells: List[CellCoordinate]): List[CellCoordinate] = {
    allDeadNeighbourCells filter { deadCell =>
      val aliveNeighbours = getAliveAndDeadNeighbours(deadCell, petriDish)
      deadCellWillBeReproducedOnto(aliveNeighbours.alive)
    }
  }
}
