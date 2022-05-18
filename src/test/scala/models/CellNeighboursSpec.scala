package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CellNeighboursSpec extends AnyWordSpec with Matchers {

  "models.CellNeighbours .getNeighbourCoordinates" when {

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

        CellNeighbours.getNeighbourCoordinates(originalCell) shouldEqual expectedNeighbours
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

        CellNeighbours.getNeighbourCoordinates(originalCell) shouldEqual expectedNeighbours
      }
    }
  }
}
