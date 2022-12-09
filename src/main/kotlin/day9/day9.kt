package day9

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Paths

data class Point(var x: Int, var y:Int)

fun main(){
    val rootDir = Paths.get(".").normalize().toAbsolutePath()
    var file : File = File("$rootDir/src/main/kotlin/day9/input.txt")
    var reader : BufferedReader = BufferedReader(FileReader(file))

    var str : String?

    val visitedPlaces : MutableList<Point> = mutableListOf()
    val points : List<Point> = List(2){Point(0,0)} // size 2 for part 1
    val head = points.last()
    while(true) {
        str = reader.readLine()
        if (str == null) {
            break
        }
        val split = str.split(" ")
        for (i in 0 until split[1].toInt()) {
            when (split[0]) {
                "U" -> head.y = head.y + 1
                "D" -> head.y = head.y - 1
                "L" -> head.x = head.x - 1
                "R" -> head.x = head.x + 1
            }

            for(i in points.size-1 downTo 1){
                if(!touchingTail(points[i], points[i - 1])){
                    val t = moveDirection(points[i], points[i-1])
                    points[i - 1].x += t.x
                    points[i - 1].y += t.y
                }
            }

            if(!touchingTail(points[1], points.first())){
                val t = moveDirection(points.first(), points[1])
                points.first().x += t.x
                points.first().y += t.y
            }

            val point = Point(points.first().x, points.first().y)
            visitedPlaces.add(point)
        }
    }
    val uniquePlaces = visitedPlaces.toSet().toList()

    println("Total places visited: ${uniquePlaces.size}")
}

fun moveDirection(head: Point, tail: Point) : Point {
    return Point(Integer.signum(head.x - tail.x), Integer.signum(head.y - tail.y))
}

fun touchingTail(head: Point, tail: Point) : Boolean{
    if( (tail.x == head.x + 1 && tail.y == head.y) ||
        (tail.x == head.x - 1 && tail.y == head.y) ||
        (tail.y == head.y + 1 && tail.x == head.x) ||
        (tail.y == head.y - 1 && tail.x == head.x) ||
        (tail.x == head.x + 1 && tail.y == head.y + 1 )||
        (tail.x == head.x - 1 && tail.y == head.y + 1) ||
        (tail.x == head.x + 1 && tail.y == head.y - 1) ||
        (tail.x == head.x - 1 && tail.y == head.y - 1) ||
        (tail.x == head.x && tail.y == head.y)) {
        return true
    }
    return false
}