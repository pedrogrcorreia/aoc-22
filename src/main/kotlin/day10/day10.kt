package day10

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day10/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?

    val instructions : MutableList<Int> = mutableListOf()
    var x = 1
    var spritePosition = 1
    var screen : MutableList<MutableList<Char>> = MutableList(6){MutableList(40){'.'} }
    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }

        val split = str.split(" ")

        when(split[0]) {
            "noop" -> {
                instructions.add(x)
            }

            "addx" -> {
                instructions.add(x)
                instructions.add(x)
                x += split[1].toInt()
            }
        }
    }

    println("Signal strength: ${(instructions[19] * 20) + (instructions[59] * 60) + 
            (instructions[99] * 100) + (instructions[139] * 140) + 
            (instructions[179] * 180) + (instructions[219] * 220)}")

    var index = 0
    var line = 0
    var column = 0

    println("Monitor view: ")
    for(i in 0 until screen.size){
        for(j in 0 until screen[i].size){
            spritePosition = instructions[index + column * 40]
            if(spritePosition in listOf(index - 1, index, index +1)){
                screen[line][j] = '#'
            }
            print(screen[i][j])
            index++
        }
        line++
        column++
        index = 0
        println()
    }
}