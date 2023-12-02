package day01

import java.io.File

val example = """1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet"""
val example2 = """two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen"""

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

val DIGITS = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun solve2(input: List<String>): Int {
    fun findFirstNumberOrDigit(line: String, digits: List<String> = DIGITS): Int {
        return line.findAnyOf(digits)
            ?.let { (indexOfFirst, match) ->
                    if (line.indexOfFirst { it.isDigit() }.let { (it == -1) or (it > indexOfFirst)}) {
                        digits.indexOf(match) + 1
                    }
                    else {
                        line.first { it.isDigit() }.digitToInt()
                    }
            } ?: line.first { it.isDigit() }.digitToInt()
    }
    return input.asSequence()
        .map { line ->
            listOf(
                findFirstNumberOrDigit(line),
                findFirstNumberOrDigit(line.reversed(), DIGITS.map { it.reversed() })
            ).joinToString("")
        }
        .map(String::toInt)
        .sum()
}

fun main() {
    val input =
        File("/home/timj/docs/repo/GitHub/tim-jacobson/advent-of-code-2023/src/main/resources/day01/input").readLines()
    solve2(input).also { println(it) }
}