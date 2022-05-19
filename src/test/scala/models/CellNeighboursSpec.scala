package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CellNeighboursSpec extends AnyWordSpec with Matchers {

  "CellNeighbours .getNeighbourCoordinatesForCell" when {

    "original cell coordinates are at least 1,1" should {

      "return coordinates of all 'neighbour' cells" in {
        val originalCell = CellCoordinate(2, 2)

        /*

          X = Original cell
          N = Neighbouring cell

          +-----+-----+-----+-----+-----+-----+
          |     |  0  |  1  |  2  |  3  |  4  |
          +-----------------------------------+
          |  0  |     |     |     |     |     |
          +-----------------------------------+
          |  1  |     |  N  |  N  |  N  |     |
          +-----------------------------------+
          |  2  |     |  N  |  X  |  N  |     |
          +-----------------------------------+
          |  3  |     |  N  |  N  |  N  |     |
          +-----------------------------------+
          |  4  |     |     |     |     |     |
          +-----+-----+-----+-----+-----+-----+

        */

        val expectedNeighbours = List(
          CellCoordinate(1,1),
          CellCoordinate(2,1),
          CellCoordinate(3,1),
          CellCoordinate(1,2),
          CellCoordinate(3,2),
          CellCoordinate(1,3),
          CellCoordinate(2,3),
          CellCoordinate(3,3)
        )

        CellNeighbours.getNeighbourCoordinatesForCell(originalCell) shouldEqual expectedNeighbours
      }
    }

    "original cell coordinates are below 1,1" should {

      "remove any negative calculated coordinates" in {
        val originalCell = CellCoordinate(0, 0)

        /*

          X = Original cell
          N = Neighbouring cell

          +-----+-----+-----+-----+-----+-----+
          |     |  0  |  1  |  2  |  3  |  4  |
          +-----------------------------------+
          |  0  |  X  |  N  |     |     |     |
          +-----------------------------------+
          |  1  |  N  |  N  |     |     |     |
          +-----------------------------------+
          |  2  |     |     |     |     |     |
          +-----------------------------------+
          |  3  |     |     |     |     |     |
          +-----------------------------------+
          |  4  |     |     |     |     |     |
          +-----+-----+-----+-----+-----+-----+

        */

        val expectedNeighbours = List(
          CellCoordinate(1,0),
          CellCoordinate(0,1),
          CellCoordinate(1,1)
        )

        CellNeighbours.getNeighbourCoordinatesForCell(originalCell) shouldEqual expectedNeighbours
      }
    }
  }

  "CellNeighbours .getAliveAndDeadNeighbours" should {

    /*

      X = original alive cell
      D = dead neighbouring cells

      +-----+-----+-----+-----+-----+-----+
      |     |  0  |  1  |  2  |  3  |  4  |
      +-----------------------------------+
      |  0  |     |  D  |  D  |  D  |     |
      +-----------------------------------+
      |  1  |     |  D  |  X  |  D  |     |
      +-----------------------------------+
      |  2  |     |  D  |  D  |  D  |     |
      +-----------------------------------+
      |  3  |     |     |     |     |     |
      +-----+-----+-----+-----+-----+-----+

    */

    val aliveCell = CellCoordinate(2, 1)
    val petriDish = PetriDish(List(aliveCell))

    val expected = CellNeighbours(
      alive = List.empty,
      dead = List(
        CellCoordinate(1, 0),
        CellCoordinate(1, 1),
        CellCoordinate(1, 2),
        CellCoordinate(2, 0),
        CellCoordinate(2, 2),
        CellCoordinate(3, 0),
        CellCoordinate(3, 1),
        CellCoordinate(3, 2)
      )
    )

    val result = CellNeighbours.getAliveAndDeadNeighbours(aliveCell, petriDish)

    "return all alive and dead neighbours for a given cell" in {
      result.alive.sorted shouldEqual expected.alive
      result.dead.sorted shouldEqual expected.dead
    }
  }
}
