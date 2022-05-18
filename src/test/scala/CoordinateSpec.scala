import Coordinate.unapply
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CoordinateSpec extends AnyWordSpec with Matchers {

  "Coordinate .apply" when {

    "input is in the format 'x,y'" should {

      "create a Coordinate" in {
        Coordinate.apply("1,2") shouldEqual Coordinate(1, 2)
      }
    }

    "input is NOT in the format 'x,y'" should {

      "throw an exception with message" in {
        assertThrows[IllegalArgumentException] {
          Coordinate.apply("a,b")
        }
      }
    }
  }

  "Coordinate .unapply" should {

    "return a correctly formatted string" in {
      unapply(Coordinate(1,2)) shouldEqual "1,2"
    }
  }
}
