package day1

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths


fun main() {
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day1/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?
    var calories : Int = 0
    var count : Int = 0
    var maxCalories : Int = 0
    var elfNumber : Int = 0

    var caloriesList: MutableList<Int> = mutableListOf()
    while(true){
        str = reader.readLine()
        if(str == null){
            break
        }
        str = str.trim()
        if(str.isEmpty()){
            println("Elf ${++count} is carrying $calories calories")
            caloriesList.add(calories)
            if(calories > maxCalories) {
                maxCalories = calories
                elfNumber = count
            }
            calories = 0
        } else if(str.isNotEmpty()){
            calories += str.toInt()
        }
    }

    // First puzzle
    println("Elf $elfNumber carries the most calories with $maxCalories calories")

    caloriesList.sort()

    // Second puzzle
    println(caloriesList.removeLast() + caloriesList.removeLast() + caloriesList.removeLast())
}