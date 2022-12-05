package day5

import day5.Stacks.list
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths
import java.util.Objects

object Stacks{
    var list : MutableList<MutableList<Char>> = mutableListOf()
}

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day5/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))



    for(i in 0 until 9){
        list.add(mutableListOf())
    }

    var str : String?

    while(true){
        str = reader.readLine()
        if (str.isEmpty() || str.contains("[1-9]".toRegex())) {
            break
        }
        //str = str.trim()
        for(i in str.indices){
            if(str[i] == ' ' || str[i] == '[' || str[i] == ']'){
                continue
            } else{
                list[i / 4].add(str[i])
            }
        }
    }
    reverseLists()
    while(true){
        str = reader.readLine()
        if(str == null){
            break
        }
        if(str.isBlank()){
            continue
        }
        val numbers = Regex("[0-9]+").findAll(str)
            .map(MatchResult::value)
            .toList()
        val qt = numbers[0].toInt()
        val origin = numbers[1].toInt()
        val dest = numbers[2].toInt()
        moveCrates(qt, origin, dest)
    }
    printLists()
    println("Top of each stack: ")
    for (i in list.indices){
        print(list[i].last())
    }
}

fun moveCrates(qt : Int, origin : Int, dest : Int){
    val stackToRemove = list[origin-1]
    val removingCrates = stackToRemove.subList(stackToRemove.size - qt, stackToRemove.size)
    val addingCrates = ArrayList(removingCrates)
    //addingCrates.reverse() // comment for part 2
    val destStack = list[dest-1]
    removeCrates(qt, list[origin - 1])
    destStack.addAll(addingCrates)
}

fun printLists(){
    println("Lists: ")
    for(i in 0 until 9){
        println(list[i])
    }
    println()
}

fun reverseLists(){
    for(i in 0 until 9){
        list[i].reverse()
    }
}

fun removeCrates(qt : Int, list : MutableList<Char>){
    for(i in list.size - 1 downTo list.size - qt){
        list.removeAt(i)
    }
}