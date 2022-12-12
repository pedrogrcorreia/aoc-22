package day11

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

data class Item(var worryLevel: Long)

data class Monkey(val items: MutableList<Item>, val operation: String, val operationValue: Int?, val test: Int, val monkeys: Array<Int>){
    var inspectedItems = 0

    fun inspectItem(index: Int, lcm: Int, relief: Int) : Int {
            val item = items[index]
            item.worryLevel = calculate(item.worryLevel, operation, operationValue?.toLong() ?: item.worryLevel)
            item.worryLevel = item.worryLevel % lcm
            item.worryLevel = item.worryLevel / relief
            inspectedItems++
            return if(item.worryLevel % test == 0L){
                monkeys[0]
            } else{
                monkeys[1]
            }
    }
}

data class Monkeys(val monkeys: List<Monkey>){
    fun round(lcm: Int, relief: Int){
        for(monkey in monkeys){
            for(i in 0 until monkey.items.size){
                val newMonkey = monkey.inspectItem(i, lcm, relief)
                monkeys[newMonkey].items.add(monkey.items[i])
            }
            monkey.items.clear()
        }
    }
}

fun main() {
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file: File = File("$rootDir/src/main/kotlin/day11/input.txt")
    var reader: BufferedReader = BufferedReader(FileReader(file))

    var str: String?

    var monkeysString : MutableList<String> = mutableListOf()
    var monkeyString : String = ""
    while (true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        while(str?.isNotBlank() == true){
            monkeyString += "$str \n"
            str = reader.readLine()
        }
        monkeysString.add(monkeyString)
        monkeyString = ""
    }

    // Part 1
    val monkeysFirst = Monkeys(parseStringArray(monkeysString))
    val monkeysSecond = Monkeys(parseStringArray(monkeysString))
    val lcm = getLCM(monkeysFirst.monkeys)
    for(i in 0 until 20){
        monkeysFirst.round(lcm, 3)
    }

    val resultsFirst = getResults(monkeysFirst.monkeys)

    println("Part 1 - Monkey business after 20 rounds with relief: ${resultsFirst.removeLast() * resultsFirst.removeLast()}")

    // Part 2
    for(i in 0 until 10000){
        monkeysSecond.round(lcm, 1)
    }

    val resultsSecond = getResults(monkeysSecond.monkeys)
    println("Part 2 - Monkey business after 10000 rounds without relief: ${resultsSecond.removeLast() * resultsSecond.removeLast()}")
}

fun parseStringArray(monkeysString: List<String>) : List<Monkey>{
    val monkeys = mutableListOf<Monkey>()
    for(monkey in monkeysString){
        val splitMonkey = monkey.split("\r?\n|\r".toRegex())
        val itemsValue = getNumbersFromString(splitMonkey[1])
        val items = mutableListOf<Item>()
        val operation : String = getOperationsFromString(splitMonkey[2])
        val operationValue : Int? = if(getNumbersFromString(splitMonkey[2]).isEmpty()){
            null
        } else {
            getNumbersFromString(splitMonkey[2]).first().toInt()
        }
        val test = getNumbersFromString(splitMonkey[3]).first().toInt()
        val monkeyIfTrue = getNumbersFromString(splitMonkey[4]).first().toInt()
        val monkeyIfFalse = getNumbersFromString(splitMonkey[5]).first().toInt()
        val monkeysToThrow = arrayOf(monkeyIfTrue, monkeyIfFalse)
        for(element in itemsValue){
            items.add(Item(element.toLong()))
        }

        monkeys.add(Monkey(items, operation, operationValue, test, monkeysToThrow))
    }
    return monkeys
}

fun getLCM(monkeys: List<Monkey>) : Int {
    var lcm = 1
    for(monkey in monkeys){
        lcm *= monkey.test
    }
    return lcm
}

fun getNumbersFromString(string: String) : List<String>{
    return Regex("[0-9]+").findAll(string)
        .map(MatchResult::value)
        .toList()
}

fun getOperationsFromString(string: String) : String{
    for(char in string){
        when(char){
            '*' -> return "*"
            '+' -> return "+"
            '-' -> return "-"
            '/' -> return "/"
        }
    }
    return "+"
}

fun calculate(firstOperator: Long, operation: String, secondOperator: Long) : Long{
    when(operation){
        "+" -> return firstOperator.plus(secondOperator)
        "-" -> return firstOperator.minus(secondOperator)
        "/" -> return firstOperator.div(secondOperator)
        "*" -> return firstOperator.times(secondOperator)
    }

    return -1
}

fun getResults(monkeys: List<Monkey>) : MutableList<Long>{
    val results = mutableListOf<Long>()
    for(monkey in monkeys){
        results.add(monkey.inspectedItems.toLong())
    }
    results.sort()
    return results
}