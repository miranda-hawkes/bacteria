import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PetriDishSpec extends AnyWordSpec with Matchers {

  "PetriDish .apply" when {

    "input is valid" should {

      "create a PetriDish containing list of CellCoordinate values" in {
        val input = List(
          "1 , 2",
          "2 , 2",
          "3 , 2",
          "1000000001 , 1000000002",
          "1000000002 , 1000000002",
          "1000000003 , 1000000002"
        )
        val expectedResult = PetriDish(List(
          CellCoordinate(1,2),
          CellCoordinate(2,2),
          CellCoordinate(3,2),
          CellCoordinate(1000000001, 1000000002),
          CellCoordinate(1000000002, 1000000002),
          CellCoordinate(1000000003, 1000000002)
        ))
        PetriDish(input) shouldEqual expectedResult
      }
    }

    "input is invalid" should {

      "cascade error thrown from CellCoordinate" in {
        val input = List(
          "1 , 2",
          "2 , 2 , 3 , 4"
        )

        assertThrows[IllegalArgumentException] {
          PetriDish(input)
        }
      }
    }
  }
}
