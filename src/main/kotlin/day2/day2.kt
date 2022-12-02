package day2

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

object Moves{
    const val ROCK = 1
    const val PAPER = 2
    const val SCISSORS = 3
}

object Result{
    const val WIN = 1
    const val DRAW = 2
    const val LOSE = 3

    fun getPoints(result: Int) : Int{
        return when(result){
            WIN -> 6
            DRAW -> 3
            LOSE -> 0
            else -> 0
        }
    }
}

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day2/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?

    var totalPoints = 0
    var totalPointsSecond = 0

    while(true){
        var roundPoints = 0

        str = reader.readLine()
        if(str == null){
            break
        }
        val splitStr = str.split(" ")

        val elfMove = getMove(splitStr[0])
        val myMove = getMove(splitStr[1])
        val result = getResult(splitStr[1])

        roundPoints += myMove
        println("You got $roundPoints for playing ${splitStr[1]}")

        roundPoints += getWinnerPoints(elfMove, myMove)
        totalPoints += roundPoints

        var roundPointsSecond = 0
        roundPointsSecond += getNeedMove(result, elfMove)
        roundPointsSecond += Result.getPoints(result)

        totalPointsSecond += roundPointsSecond
    }
    // First puzzle
    println("Total points: $totalPoints")

    // Second puzzle
    println("Total points: $totalPointsSecond")
}

fun getWinnerPoints(shapeElf: Int, myShape: Int): Int{
    if(shapeElf == myShape){
        return 3
    }
    if(shapeElf == Moves.PAPER && myShape == Moves.ROCK ||
            shapeElf == Moves.SCISSORS && myShape == Moves.PAPER ||
            shapeElf == Moves.ROCK && myShape == Moves.SCISSORS){
        return 0
    }
    return 6
}

fun getMove(shape: String) : Int {
    when(shape){
        in arrayOf("X", "A") -> return Moves.ROCK
        in arrayOf("Y", "B") -> return Moves.PAPER
        in arrayOf("Z", "C") -> return Moves.SCISSORS
    }
    return 0
}

fun getResult(result: String) : Int {
    when(result){
        "X" -> return Result.LOSE
        "Y" -> return Result.DRAW
        "Z" -> return Result.WIN
    }
    return 0
}

fun getNeedMove(result: Int, elfMove: Int): Int{
    if(result == Result.DRAW){
        return elfMove
    }
    if(result == Result.WIN){
        when(elfMove){
           Moves.ROCK -> return Moves.PAPER
           Moves.PAPER -> return Moves.SCISSORS
           Moves.SCISSORS -> return Moves.ROCK
        }
    }
    if(result == Result.LOSE){
        when(elfMove){
            Moves.ROCK -> return Moves.SCISSORS
            Moves.PAPER -> return Moves.ROCK
            Moves.SCISSORS -> return Moves.PAPER
        }
    }
    return 0
}