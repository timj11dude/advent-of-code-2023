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
data class PartNumberCoordinate(val coordinates: Coordinates, val number: Int) {
    val encapturedArea: List<Coordinates> = (0..log(number.toDouble(),10.0).toInt())
        .map { coordinates.copy(first = coordinates.first + it) }
}
typealias Coordinates = Pair<Int,Int>
val Coordinates.x get() = first
val Coordinates.y get() = second
val Coordinates.surrounding: List<Coordinates> get() = (-1..1).flatMap { y -> (-1..1).map { x -> x to y } }
    .filter { it != (0 to 0) }
    .map { (it.x + this.x) to (it.y + this.y) }

fun solve(solver: (List<String>) -> Int, input: String): Int = solver(input.split("\n"))
fun solve(input: List<String>): Int {
    val fieldOfNumbers = mutableListOf<PartNumberCoordinate>()
    input.forEachIndexed { yIndex, line ->
        var collection = ""
        var index = -1
        line.forEachIndexed { xIndex, c ->
            if (c.isDigit()) {
                if(collection.isEmpty()) index = xIndex
                collection += c
            } else {
                if (collection.isNotEmpty()) {
                    fieldOfNumbers += PartNumberCoordinate(index to yIndex, collection.toInt())
                    collection = ""
                }
            }
        }
        if (collection.isNotEmpty()) fieldOfNumbers += PartNumberCoordinate(index to yIndex, collection.toInt())
    }
    fun searchPart(coords: Coordinates): Set<Char> = coords.surrounding
        .mapNotNull { search ->
            if ((search.x < 0) or (search.y < 0) or (search.x >= input[0].length) or (search.y >= input.size)) null
            else input[search.y][search.x].takeUnless { it.isDigit() or (it == '.') }
        }.toSet()
    fun searchPart(coords: PartNumberCoordinate): Set<Char> = coords.encapturedArea.flatMap { coord -> searchPart(coord) }.toSet()
    val partNumbers = fieldOfNumbers.flatMap { e -> searchPart(e).map { PartNumber(it, e.number) } }
    return partNumbers.sumOf { it.number }
}

fun solve2(input: List<String>): Int {
    val fieldOfNumbers = mutableListOf<PartNumberCoordinate>()
    input.forEachIndexed { yIndex, line ->
        var collection = ""
        var index = -1
        line.forEachIndexed { xIndex, c ->
            if (c.isDigit()) {
                if(collection.isEmpty()) index = xIndex
                collection += c
            } else {
                if (collection.isNotEmpty()) {
                    fieldOfNumbers += PartNumberCoordinate(index to yIndex, collection.toInt())
                    collection = ""
                }
            }
        }
        if (collection.isNotEmpty()) fieldOfNumbers += PartNumberCoordinate(index to yIndex, collection.toInt())
    }
    val gears: List<Int> = input.flatMapIndexed { yIndex: Int, line: String ->
        line.mapIndexedNotNull { xIndex, c ->
            if (c == '*') {
                (xIndex to yIndex).surrounding
                    .let { s -> fieldOfNumbers.filter { (it.encapturedArea intersect s.toSet()).isNotEmpty() } }
                    .let { search -> if (search.size == 2) search.first().number * search.last().number else null }
            }
            else null
        }
    }
    return gears.sum()
}

fun main() {
    val input =
        File("/home/timj/docs/repo/GitHub/tim-jacobson/advent-of-code-2023/src/main/resources/day03/input").readLines()
//    solve(::solve, example)
//    solve(input)
//    solve(::solve2, example)
    solve2(input)
        .also { println(it) }
}