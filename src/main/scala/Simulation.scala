import models.{CellCoordinate, PetriDish}
import models.PetriDish._

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}

object Simulation extends App {

  def start(): Unit = {

    println("+==================+")
    println("*     Bacteria     *")
    println("+==================+")
    println()
    println("Press ^C to quit")
    println("Enter pairs of coordinates separated by a comma (e.g. 1,2), each followed by enter")
    println("When finished type 'end' followed by enter")
    println()

    val originalPetriDish = PetriDish(gatherUserInput(List.empty))

    println()
    println("Calculating next generation...")
    println()

    val newGeneration = new CalculateNextGeneration(originalPetriDish).nextGeneration

    if(newGeneration.liveBacteria.isEmpty) {
      println("Zero bacteria live onto next generation!")
    } else {
      println(unapply(newGeneration))
      println("end")
    }
  }

  @tailrec
  def gatherUserInput(bacteria: List[CellCoordinate]): List[CellCoordinate] = {
    val newInput = readLine()

    if(newInput == "end") bacteria
    else {
      Try(CellCoordinate(newInput)) match {
        case Success(newBacteriaCoordinates) =>
          if(bacteria.contains(newBacteriaCoordinates)) {
            outputErrorPrompt("Coordinates already input")
            gatherUserInput(bacteria)
          } else gatherUserInput(bacteria :+ newBacteriaCoordinates)
        case Failure(exception) =>
          outputErrorPrompt(exception.getMessage)
          gatherUserInput(bacteria)
      }
    }
  }

  private def outputErrorPrompt(errorText: String): Unit = {
    println(s"ERROR: $errorText")
    println("Try again")
    println("")
  }

  start()
}
