package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import models.CellCoordinate._

class CellCoordinateSpec extends AnyWordSpec with Matchers {

  "CellCoordinate .apply" when {

    "input is in the format 'x,y'" should {

      "create a CellCoordinate" in {
        CellCoordinate.apply("1,2") shouldEqual CellCoordinate(1, 2)
      }
    }

    "input is in the format 'x , y'" should {

      "create a CellCoordinate" in {
        CellCoordinate.apply("1 , 2") shouldEqual CellCoordinate(1, 2)
      }
    }

    "input is NOT in a valid format" when {

      "input contains non-digits" should {

        "throw an exception with message" in {
          val result = intercept[IllegalArgumentException] {
            CellCoordinate.apply("a,b")
          }

          result.getMessage shouldEqual "Input contains non-digits"
        }
      }

      "input contains more than two comma-separated characters" should {

        "throw an exception with message" in {
          val result = intercept[IllegalArgumentException] {
            CellCoordinate.apply("1,2,3,4")
          }

          result.getMessage shouldEqual "Input should contain one comma"
        }
      }
    }
  }

  "CellCoordinate .unapply" should {

    "return a correctly formatted string" in {
      unapply(CellCoordinate(1,2)) shouldEqual "1 , 2"
    }
  }

  "CellCoordinate .sort" should {

    "correctly sort a pair of coordinates by x then y" in {
      val unsorted = List(
        CellCoordinate(5, 2),
        CellCoordinate(5, 1),
        CellCoordinate(3, 2),
        CellCoordinate(3, 1)
      )

      val sorted = List(
        CellCoordinate(3, 1),
        CellCoordinate(3, 2),
        CellCoordinate(5, 1),
        CellCoordinate(5, 2)
      )

      unsorted.sorted shouldEqual sorted
    }
  }
}
