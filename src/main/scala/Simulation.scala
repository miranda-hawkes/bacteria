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

    val originalBacteriaCoordinates = PetriDish(gatherUserInput(List.empty))

    println(unapply(originalBacteriaCoordinates))
  }

  @tailrec
  def gatherUserInput(bacteria: List[CellCoordinate]): List[CellCoordinate] = {
    val newInput = readLine()
    if(newInput == "end") bacteria
    else {
      Try(CellCoordinate(newInput)) match {
        case Success(newBacteriaCoordinates) =>
          if(bacteria.contains(newBacteriaCoordinates)) {
            println("ERROR: Coordinates already input")
            println("Try again")
            println("")
            gatherUserInput(bacteria)
          } else gatherUserInput(bacteria :+ newBacteriaCoordinates)
        case Failure(exception) =>
          println("ERROR: " + exception.getMessage)
          println("Try again")
          println()
          gatherUserInput(bacteria)
      }
    }
  }

  start()
}
