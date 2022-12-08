package day8

import day7.Directory
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day8/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?
    var map : MutableList<MutableList<Int>> = mutableListOf()

    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        val newLine : MutableList<Int> = mutableListOf()
        for(number in str){
            newLine.add(number.digitToInt())
        }
        map.add(newLine)
    }

    var visible = 0

    for(i in 1 until map.size-1){
        for(j in 1 until map[i].size-1) {
            if(verifyRight(j, map[i]) || verifyLeft(j, map[i])){
                visible++
                continue
            }
            if(verifyTop(i, j, map)){
                visible++
                continue
            }
            if(verifyBot(i, j, map)){
                visible++
                continue
            }
        }
    }

    var scenicTotal = 0

    for(i in 1 until map.size-1){
        for(j in 1 until map[i].size-1) {
            var scenicRight = verifyRightScore(j, map[i])
            var scenicLeft = verifyLeftScore(j, map[i])
            var scenicTop = verifyTopScore(i, j, map)
            var scenicBot = verifyBotScore(i, j, map)
            var thisScenic = scenicRight * scenicLeft * scenicTop * scenicBot
            if(thisScenic > scenicTotal){
                scenicTotal = thisScenic
            }
        }
    }

    val outerTrees = (map.size * 2) + map[0].size * 2 - 4
    println("Visible trees: ${visible + outerTrees}")
    println("Most scenic score: $scenicTotal")
}

fun verifyRight(pos: Int, list: List<Int>) : Boolean{
    val value = list[pos]
    for(i in pos+1 until list.size){
        if(list[i] >= value){
            return false
        }
    }
    return true
}

fun verifyRightScore(pos: Int, list: List<Int>): Int{
    val value = list[pos]
    var scenicScore = 0
    for(i in pos+1 until list.size){
        scenicScore++
        if(list[i] >= value){
            break
        }
    }
    return scenicScore
}

fun verifyLeft(pos: Int, list: List<Int>) : Boolean{
    val value = list[pos]
    for(i in pos-1 downTo 0){
        if(list[i] >= value){
            return false
        }
    }
    return true
}

fun verifyLeftScore(pos: Int, list: List<Int>) : Int{
    val value = list[pos]
    var scenicScore = 0
    for(i in pos-1 downTo 0){
        scenicScore++
        if(list[i] >= value){
            break
        }
    }
    return scenicScore
}

fun verifyTop(y: Int, x: Int, list: List<List<Int>>) : Boolean{
    val value = list[y][x]
    for(i in y - 1 downTo 0){
        if(list[i][x] >= value){
            return false
        }
    }
    return true
}

fun verifyTopScore(y: Int, x: Int, list: List<List<Int>>) : Int{
    val value = list[y][x]
    var scenicScore = 0
    for(i in y - 1 downTo 0){
        scenicScore++
        if(list[i][x] >= value){
            break
        }
    }
    return scenicScore
}

fun verifyBot(y: Int, x: Int, list: List<List<Int>>) : Boolean {
    val value = list[y][x]
    for(i in y + 1 until list.size){
        if(list[i][x] >= value){
            return false
        }
    }
    return true
}

fun verifyBotScore(y: Int, x: Int, list: List<List<Int>>) : Int {
    val value = list[y][x]
    var scenicScore = 0
    for(i in y + 1 until list.size){
        scenicScore++
        if(list[i][x] >= value){
            break
        }
    }
    return scenicScore
}