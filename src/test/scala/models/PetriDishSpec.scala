package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import models.PetriDish._

class PetriDishSpec extends AnyWordSpec with Matchers {

  "PetriDish .unapply" should {

    "return a correctly formatted string" in {
      val input = PetriDish(List(
        CellCoordinate(1,2),
        CellCoordinate(2,2),
        CellCoordinate(3,2),
        CellCoordinate(1000000001, 1000000002),
        CellCoordinate(1000000002, 1000000002),
        CellCoordinate(1000000003, 1000000002)
      ))

      val expectedResult =
        """1 , 2
          |2 , 2
          |3 , 2
          |1000000001 , 1000000002
          |1000000002 , 1000000002
          |1000000003 , 1000000002""".stripMargin

      unapply(input) shouldEqual expectedResult
    }
  }
}
