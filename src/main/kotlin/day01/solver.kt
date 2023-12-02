package day01

import java.io.File

val example = """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet"""

fun solve(input: String): Int = solve(input.split("\n"))
fun solve(input: List<String>): Int {
    return input
        .map { line ->
            listOf(
                line.first { it.isDigit() },
                line.last { it.isDigit() }
            ).joinToString("")
        }
        .map(String::toInt)
        .sum()
}

fun main() {
    val input = File("/home/timj/docs/repo/GitHub/tim-jacobson/advent-of-code-2023/src/main/resources/day01/input").readLines()
    solve(input).also { println(it) }
}