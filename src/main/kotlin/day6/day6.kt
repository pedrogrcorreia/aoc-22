package day6

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day6/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?
    var message : String = ""
    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        message = str
    }
    println("Characters to detect start of packet signal: ${getNonRepeatedChars(message, 4)}")
    println("Characters to detect start of message signal: ${getNonRepeatedChars(message, 14)}")
}

fun getNonRepeatedChars(message: String, length: Int): Int {
    for (i in message.indices) {
        var limit = i + length
        if (limit > message.length) {
            break
        }

        var repeated = 0
        for (j in i until limit) {
            for (k in j + 1 until limit) {
                if (message[j] == message[k]) {
                    repeated++
                }
            }
        }
        if (repeated == 0) {
            return limit
        }
    }
    return -1
}