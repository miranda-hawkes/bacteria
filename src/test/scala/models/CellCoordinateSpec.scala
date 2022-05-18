package models

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import models.CellCoordinate._

class CellCoordinateSpec extends AnyWordSpec with Matchers {

  "models.CellCoordinate .apply" when {

    "input is in the format 'x,y'" should {

      "create a models.CellCoordinate" in {
        CellCoordinate.apply("1,2") shouldEqual CellCoordinate(1, 2)
      }
    }

    "input is in the format 'x , y'" should {

      "create a models.CellCoordinate" in {
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

  "models.CellCoordinate .unapply" should {

    "return a correctly formatted string" in {
      unapply(CellCoordinate(1,2)) shouldEqual "1 , 2"
    }
  }
}
