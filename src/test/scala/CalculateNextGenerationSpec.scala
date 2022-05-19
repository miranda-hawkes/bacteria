import models.{CellCoordinate, CellNeighbours, PetriDish}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CalculateNextGenerationSpec extends AnyWordSpec with Matchers {

  "CalculateNextGeneration .calculateLiveCellsToStayAlive" when {

    "all alive cells will live onto next generation" should {

      /*

        +-----+-----+-----+-----+-----+
        |     |  0  |  1  |  2  |  3  |
        +-----------------------------+
        |  0  |  X  |  X  |     |     |
        +-----------------------------+
        |  1  |  X  |     |     |     |
        +-----------------------------+
        |  2  |     |     |     |     |
        +-----------------------------+
        |  3  |     |     |     |     |
        +-----+-----+-----+-----+-----+

      */

      val aliveCellsAndNeighbours: Map[CellCoordinate, CellNeighbours] = Map(
        CellCoordinate(0, 0) -> CellNeighbours(
          alive = List(
            CellCoordinate(1, 0),
            CellCoordinate(0, 1)
          ),
          dead = List.empty
        ),
        CellCoordinate(1, 0) -> CellNeighbours(
          alive = List(
            CellCoordinate(0, 0),
            CellCoordinate(0, 1)
          ),
          dead = List.empty
        ),
        CellCoordinate(0, 1) -> CellNeighbours(
          alive = List(
            CellCoordinate(0, 0),
            CellCoordinate(1, 0)
          ),
          dead = List.empty
        )
      )

      val calculation = new CalculateNextGeneration(PetriDish(List.empty))

      "return all original alive cells" in {
        calculation.calculateLiveCellsToStayAlive(aliveCellsAndNeighbours) shouldEqual aliveCellsAndNeighbours.keys.toList
      }
    }

    "some alive cells will live onto next generation" should {

      /*

        +-----+-----+-----+-----+-----+
        |     |  0  |  1  |  2  |  3  |
        +-----------------------------+
        |  0  |  X  |  X  |     |     |
        +-----------------------------+
        |  1  |  X  |     |     |     |
        +-----------------------------+
        |  2  |     |  X  |     |     |
        +-----------------------------+
        |  3  |     |     |     |     |
        +-----+-----+-----+-----+-----+

      */

      val aliveCellsAndNeighbours: Map[CellCoordinate, CellNeighbours] = Map(
        CellCoordinate(0, 0) -> CellNeighbours(
          alive = List(
            CellCoordinate(1, 0),
            CellCoordinate(0, 1)
          ),
          dead = List.empty
        ),
        CellCoordinate(1, 0) -> CellNeighbours(
          alive = List(
            CellCoordinate(0, 0),
            CellCoordinate(0, 1)
          ),
          dead = List.empty
        ),
        CellCoordinate(0, 1) -> CellNeighbours(
          alive = List(
            CellCoordinate(0, 0),
            CellCoordinate(1, 0)
          ),
          dead = List.empty
        ),
        CellCoordinate(1, 2) -> CellNeighbours(
          alive = List(
            CellCoordinate(0, 1)
          ),
          dead = List.empty
        )
      )

      val calculation = new CalculateNextGeneration(PetriDish(List.empty))

      "return the correct alive cells" in {
        calculation.calculateLiveCellsToStayAlive(aliveCellsAndNeighbours) shouldEqual
          aliveCellsAndNeighbours.removed(CellCoordinate(1, 2)).keys.toList
      }
    }

    "no alive cells will live onto next generation" should {

      /*

        +-----+-----+-----+-----+-----+
        |     |  0  |  1  |  2  |  3  |
        +-----------------------------+
        |  0  |     |     |     |  X  |
        +-----------------------------+
        |  1  |  X  |     |     |     |
        +-----------------------------+
        |  2  |     |  X  |     |     |
        +-----------------------------+
        |  3  |     |     |     |     |
        +-----+-----+-----+-----+-----+

      */

      val aliveCellsAndNeighbours: Map[CellCoordinate, CellNeighbours] = Map(
        CellCoordinate(0, 1) -> CellNeighbours(
          alive = List(
            CellCoordinate(1, 2)
          ),
          dead = List.empty
        ),
        CellCoordinate(1, 2) -> CellNeighbours(
          alive = List(
            CellCoordinate(0, 1)
          ),
          dead = List.empty
        ),
        CellCoordinate(3, 0) -> CellNeighbours(
          alive = List.empty,
          dead = List.empty
        )
      )

      val calculation = new CalculateNextGeneration(PetriDish(List.empty))

      "return no alive cells" in {
        calculation.calculateLiveCellsToStayAlive(aliveCellsAndNeighbours) shouldEqual List.empty
      }
    }
  }

  "CalculateNextGeneration .calculateReproductionOntoDeadCells" when {

    "some dead cells have exactly 3 alive neighbours and some do not" should {

      /*

        D = dead cell that neighbours an alive cell
        i.e. has the potential for reproduction

        +-----+-----+-----+-----+-----+-----+
        |     |  0  |  1  |  2  |  3  |  4  |
        +-----------------------------------+
        |  0  |     |  D  |  D  |  D  |     |
        +-----------------------------------+
        |  1  |     |  D  |  X  |  D  |     |
        +-----------------------------------+
        |  2  |     |  D  |  X  |  D  |     |
        +-----------------------------------+
        |  3  |     |  D  |  X  |  D  |     |
        +-----------------------------------+
        |  4  |     |  D  |  D  |  D  |     |
        +-----+-----+-----+-----+-----+-----+

      */

      val allDeadNeighbouringAliveCells: List[CellCoordinate] = List(
        CellCoordinate(1, 0), CellCoordinate(1, 1), CellCoordinate(1, 2), CellCoordinate(1, 3), CellCoordinate(1, 4),
        CellCoordinate(2, 0),
        CellCoordinate(3, 0), CellCoordinate(3, 1), CellCoordinate(3, 2), CellCoordinate(3, 3), CellCoordinate(3, 4),
      )

      val petriDish = PetriDish(liveBacteria = List(
        CellCoordinate(2, 1),
        CellCoordinate(2, 2),
        CellCoordinate(2, 3)
      ))

      val calculation = new CalculateNextGeneration(petriDish)

      val newAliveCells = List(
        CellCoordinate(1, 2),
        CellCoordinate(3, 2)
      )

      "return correct dead cells to be reproduced onto" in {
        calculation.calculateReproductionOntoDeadCells(allDeadNeighbouringAliveCells) shouldEqual newAliveCells
      }
    }
  }

  "CalculateNextGeneration .nextGeneration" when {

    /*

      Before:
      +------------+-----+-----+-----+-----+-----+------------+------------+------------+
      |            |  0  |  1  |  2  |  3  | ... | 1000000001 | 1000000002 | 1000000003 |
      +---------------------------------------------------------------------------------+
      |      0     |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |      1     |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |      2     |     |  X  |  X  |  X  |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |      3     |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |    ...     |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      | 1000000001 |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      | 1000000002 |     |     |     |     |     |      X     |     X      |      X     |
      +---------------------------------------------------------------------------------+
      | 1000000003 |     |     |     |     |     |            |            |            |
      +------------+-----+-----+-----+-----+-----+------------+------------+------------+

      After:
      +------------+-----+-----+-----+-----+-----+------------+------------+------------+
      |            |  0  |  1  |  2  |  3  | ... | 1000000001 | 1000000002 | 1000000003 |
      +---------------------------------------------------------------------------------+
      |      0     |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |      1     |     |     |  X  |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |      2     |     |     |  X  |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |      3     |     |     |  X  |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      |    ...     |     |     |     |     |     |            |            |            |
      +---------------------------------------------------------------------------------+
      | 1000000001 |     |     |     |     |     |            |     X      |            |
      +---------------------------------------------------------------------------------+
      | 1000000002 |     |     |     |     |     |            |     X      |            |
      +---------------------------------------------------------------------------------+
      | 1000000003 |     |     |     |     |     |            |     X      |            |
      +------------+-----+-----+-----+-----+-----+------------+------------+------------+
    */

    "supplied with original test scenario (with reproducing onto dead cells)" should {

      val petriDish = PetriDish(List(
        CellCoordinate(1, 2),
        CellCoordinate(2, 2),
        CellCoordinate(3, 2),
        CellCoordinate(1000000001, 1000000002),
        CellCoordinate(1000000002, 1000000002),
        CellCoordinate(1000000003, 1000000002)
      ))

      val simulation = new CalculateNextGeneration(petriDish)

      "output correct simulation result (sorted by ascending x,y)" in {

        val result = simulation.nextGeneration

        val expectedAfterSimulation = PetriDish(List(
          CellCoordinate(2, 1),
          CellCoordinate(2, 2),
          CellCoordinate(2, 3),
          CellCoordinate(1000000002, 1000000001),
          CellCoordinate(1000000002, 1000000002),
          CellCoordinate(1000000002, 1000000003)
        ))

        result shouldEqual expectedAfterSimulation
      }
    }

    "scenario does not result in any dead cells to be reproduced onto" should {

      /*

        Before:
        +-----+-----+-----+-----+-----+
        |     |  0  |  1  |  2  |  3  |
        +-----------------------------+
        |  0  |     |     |     |     |
        +-----------------------------+
        |  1  |  X  |     |     |     |
        +-----------------------------+
        |  2  |     |  X  |     |     |
        +-----------------------------+
        |  3  |     |     |  X  |     |
        +-----+-----+-----+-----+-----+

        After:
        +-----+-----+-----+-----+-----+
        |     |  0  |  1  |  2  |  3  |
        +-----------------------------+
        |  0  |     |     |     |     |
        +-----------------------------+
        |  1  |     |     |     |     |
        +-----------------------------+
        |  2  |     |  X  |     |     |
        +-----------------------------+
        |  3  |     |     |     |     |
        +-----+-----+-----+-----+-----+
      */

      val petriDish = PetriDish(List(
        CellCoordinate(0, 1),
        CellCoordinate(1, 2),
        CellCoordinate(2, 3)
      ))

      val simulation = new CalculateNextGeneration(petriDish)

      "output correct simulation result" in {

        val result = simulation.nextGeneration

        val expectedAfterSimulation = PetriDish(List(
          CellCoordinate(1, 2)
        ))

        result shouldEqual expectedAfterSimulation
      }
    }
  }
}
