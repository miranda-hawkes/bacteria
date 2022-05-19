import models.CellCoordinate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.{ByteArrayOutputStream, StringReader}

class SimulationSpec extends AnyWordSpec with Matchers {

  "Simulation .gatherUserInput" when {

    "inputs are valid" should {

      val inputStr =
        """|1,2
           |2,3
           |4,3
           |end
        """.stripMargin

      val input = new StringReader(inputStr)

      "return a list of CellCoordinates" in {

        val expected = List(
          CellCoordinate(1,2),
          CellCoordinate(2,3),
          CellCoordinate(4,3)
        )
        Console.withIn(input)(Simulation.gatherUserInput(List.empty)) shouldBe expected
      }
    }

    "inputs contain an invalid coordinate" should {

      val inputStr =
        """|1,2
           |2,3
           |4,3
           |not valid
           |2,2
           |end
        """.stripMargin

      val expected = List(
        CellCoordinate(1,2),
        CellCoordinate(2,3),
        CellCoordinate(4,3),
        CellCoordinate(2,2)
      )

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      "output error information and prompt to try again" in {
        Console.withOut(output) {
          Console.withIn(input)(Simulation.gatherUserInput(List.empty)) shouldBe expected
        }
      }

      "output correct prompts" in {
        output.toString should (
          include ("ERROR: Input should contain one comma") and
          include ("Try again")
        )
      }
    }

    "inputs contain an already entered (duplicate) coordinate" should {

      val inputStr =
        """|1,2
           |2,3
           |4,3
           |2,3
           |2,2
           |end
        """.stripMargin

      val expected = List(
        CellCoordinate(1,2),
        CellCoordinate(2,3),
        CellCoordinate(4,3),
        CellCoordinate(2,2)
      )

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      "output error information and prompt to try again" in {
        Console.withOut(output) {
          Console.withIn(input)(Simulation.gatherUserInput(List.empty)) shouldBe expected
        }
      }

      "output correct prompts" in {
        output.toString should (
          include ("ERROR: Coordinates already input") and
          include ("Try again")
        )
      }
    }
  }

  "Simulation .start" should {

    "correctly execute program and return correct results" in {

      val inputStr =
        """|1,2
           |2,2
           |3,2
           |1000000001,1000000002
           |1000000002,1000000002
           |1000000003,1000000002
           |end
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      Console.withOut(output) {
        Console.withIn(input)(Simulation.start())
      }

      output.toString shouldEqual
        """+==================+
          |*     Bacteria     *
          |+==================+
          |
          |Press ^C to quit
          |Enter pairs of coordinates separated by a comma (e.g. 1,2), each followed by enter
          |When finished type 'end' followed by enter
          |
          |
          |Calculating next generation...
          |
          |2 , 1
          |2 , 2
          |2 , 3
          |1000000002 , 1000000001
          |1000000002 , 1000000002
          |1000000002 , 1000000003
          |end
          |""".stripMargin
    }
  }
}
