package day03

import java.io.File
import kotlin.math.log

val example = """467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...${'$'}.*....
.664.598.."""

data class PartNumber(val part: Char, val number: Int)
typealias Coordinates = Pair<Int,Int>
val Coordinates.x get() = first
val Coordinates.y get() = second

fun solve(solver: (List<String>) -> Int, input: String): Int = solver(input.split("\n"))
fun solve(input: List<String>): Int {
    val fieldOfNumbers = mutableMapOf<Coordinates, Int>()
    input.forEachIndexed { yIndex, line ->
        var collection = ""
        var index = -1
        line.forEachIndexed { xIndex, c ->
            if (c.isDigit()) {
                if(collection.isEmpty()) index = xIndex
                collection += c
            } else {
                if (collection.isNotEmpty()) {
                    fieldOfNumbers += (index to yIndex) to collection.toInt()
                    collection = ""
                }
            }
        }
        if (collection.isNotEmpty()) fieldOfNumbers += (index to yIndex) to collection.toInt()
    }
    fun searchPart(coords: Coordinates): Set<Char> = (-1..1).flatMap { y -> (-1..1).map { x -> x to y } }.filter { it != (0 to 0) }
        .mapNotNull { search ->
            val x = coords.x + search.x
            val y = coords.y + search.y
            if ((x < 0) or (y < 0) or (x >= input[0].length) or (y >= input.size)) null
            else input[y][x].takeUnless { it.isDigit() or (it == '.') }
        }.toSet()
    fun searchPart(coords: Coordinates, number: Int) = (0..log(number.toDouble(), 10.0).toInt()).flatMap { searchPart(coords.copy(first = coords.x + it)) }.distinct()
    val partNumbers = fieldOfNumbers.flatMap { e -> searchPart(e.key, e.value).map { PartNumber(it, e.value) } }
    return partNumbers.sumOf { it.number }
}

//fun solve2(input: List<String>): Int {
//}

fun main() {
    val input =
        File("/home/timj/docs/repo/GitHub/tim-jacobson/advent-of-code-2023/src/main/resources/day03/input").readLines()
//    solve(::solve, example)
    solve(input)
//    solve(::solve2, example)
//    solve2(input)
        .also { println(it) }
}