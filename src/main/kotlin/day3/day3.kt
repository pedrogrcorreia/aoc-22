package day3

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day3/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?

    var group : MutableList<String> = mutableListOf()

    var totalValue = 0
    var totalValueGroups = 0
    var counter = 1
    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        group.add(str)

        val char : Char? = getEqualItems(str.substring(0, str.length/2), str.substring((str.length/2), str.length))
        totalValue += getPriorityValue(char!!)
        if(counter == 3){
            val char : Char? = getEqualItemsGroups(group)
            totalValueGroups += getPriorityValue(char!!)
            group.clear()
            counter = 0
        }
        counter++
    }
    // First puzzle
    println("Total priorities of items in bag: $totalValue")

    // Second puzzle
    println("Total priorities of items in groups: $totalValueGroups")
}

fun getEqualItems(firstItem : String, secondItem: String) : Char? {
    for(letterFirst in firstItem){
        for(letterSecond in secondItem){
            if(letterFirst == letterSecond){
                return letterFirst
            }
        }
    }
    return null
}

fun getEqualItemsGroups(groups : List<String>) : Char? {
    val firstItem : List<Char> = groups[0].toList()
    val secondItem : List<Char> = groups[1].toList()
    val thirdItem : List<Char> = groups[2].toList()

    val item : Set<Char> = firstItem.intersect(secondItem.toSet()).intersect(thirdItem.toSet())
    return item.elementAt(0)
}

fun getPriorityValue(char : Char) : Int {
    return if(char.isUpperCase()){
        char - 'A' + 27
    } else {
        char - 'a' + 1
    }
}