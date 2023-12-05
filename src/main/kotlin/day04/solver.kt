package day04

import java.io.File
import java.util.Queue
import kotlin.math.pow

val example = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"""

data class Card(val number: Number, val winningNums: Set<Number>, val cardNums: Set<Number>) {
    val winners: Set<Number> by lazy { winningNums intersect cardNums }
    val numberOfWinners get() = winners.size
    val points: Int by lazy {
        numberOfWinners
            .takeUnless { it == 0 }
            ?.let { winners -> 2.0.pow(winners - 1).toInt() } ?: 0
    }
}

fun solve(solver: (List<String>) -> Int, input: String): Int = solver(input.split("\n"))
fun solve(input: List<String>): Int {
    val cards = input.map { line ->
        Card(
            line.dropWhile { !it.isDigit() }.takeWhile(Char::isDigit).toInt(),
            line.dropWhile { it != ':' }.drop(1).takeWhile { it != '|' }.split(" ").filterNot(String::isBlank)
                .map(String::toInt).toSet(),
            line.dropWhile { it != '|' }.drop(1).split(" ").filterNot(String::isBlank).map(String::toInt).toSet()
        )
    }
    return cards.sumOf { it.points }
}

fun solve2(input: List<String>): Long {
    val cards = input.map { line ->
        Card(
            line.dropWhile { !it.isDigit() }.takeWhile(Char::isDigit).toInt(),
            line.dropWhile { it != ':' }.drop(1).takeWhile { it != '|' }.split(" ").filterNot(String::isBlank)
                .map(String::toInt).toSet(),
            line.dropWhile { it != '|' }.drop(1).split(" ").filterNot(String::isBlank).map(String::toInt).toSet()
        )
    }
    val stack = object {
        private val list = List(10) { 0 }.toMutableList()
        fun add(num: Int, copies: Int = 1) { list.take(num).forEachIndexed { index, i -> list[index] = i + copies } }
        fun pop(): Int {
            val top = list.removeFirst()
            list.addLast(0)
            return top
        }
    }
    return cards.fold(0L) { acc, card ->
        val copies = stack.pop()
        stack.add(card.numberOfWinners, copies+1)
        acc + (copies + 1)
    }
}

fun main() {
    val input =
        File("/home/timj/docs/repo/GitHub/tim-jacobson/advent-of-code-2023/src/main/resources/day04/input").readLines()
//    solve(::solve, example)
//    solve(input)
//    solve(::solve2, example)
    solve2(input)
        .also { println(it) }
}