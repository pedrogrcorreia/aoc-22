package day4

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day4/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?
    var totalGroups = 0
    var totalOverlaps = 0
    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        var splitStr = str.split(",")
        totalGroups += getIntersections(splitStr[0], splitStr[1])
        val firstGroupList = createList(splitStr[0])
        val secondGroupList = createList(splitStr[1])

        totalOverlaps += getIntersection(firstGroupList, secondGroupList)
    }

    println("Total groups fully contained: $totalGroups")
    println("Total groups where exists overlaps: $totalOverlaps")
}

fun getIntersections(firstGroup : String, secondGroup : String) : Int {
    val firstGroupSplit = firstGroup.split("-")
    val secondGroupSplit = secondGroup.split("-")
    if(secondGroupSplit[0].toInt() >= firstGroupSplit[0].toInt() &&
            secondGroupSplit[1].toInt() <= firstGroupSplit[1].toInt()){
        return 1
    } else if(firstGroupSplit[0].toInt() >= secondGroupSplit[0].toInt() &&
            firstGroupSplit[1].toInt() <= secondGroupSplit[1].toInt()){
        return 1
    }
    return 0
}

fun createList(group : String) : List <Int>{
    val groupSplit = group.split("-")
    val groupList = mutableListOf<Int>()
    for(i in groupSplit[0].toInt() .. groupSplit[1].toInt()){
        groupList.add(i)
    }

    return groupList
}

fun getIntersection(firstGroup : List<Int>, secondGroup: List<Int>) : Int {
    return if(firstGroup.intersect(secondGroup.toSet()).isNotEmpty()) {
        1
    } else{
        0
    }
}