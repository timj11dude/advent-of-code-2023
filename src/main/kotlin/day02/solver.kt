package day02

import java.io.File
import kotlin.math.max

val example = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"""

enum class Color {
    RED,
    GREEN,
    BLUE
}

data class Score(val color: Color, val score: Int) {

    fun isExceedingLimit(limit: Int) = score > limit

    companion object {
        fun loadScore(input: String) = Score(
            input.dropWhile { it.isDigit() or it.isWhitespace() }.let { Color.valueOf(it.trim().uppercase()) },
            input.takeWhile { it.isDigit() }.toInt()
        )
    }
}

data class Set(val scores: List<Score>) {

    fun anyScoreExceedsLimit(limits: Map<Color, Int>) = scores.any { it.isExceedingLimit(limits[it.color]!!) }

    companion object {
        fun loadSet(input: String) = Set(input.split(", ").map(Score::loadScore))
    }
}

data class Game(val id: Int, val results: List<Set>) {

    constructor(id: Int, results: String) : this(id, loadGame(results))

    fun anySetExceedsLimit(limits: Map<Color, Int>) = results.any { it.anyScoreExceedsLimit(limits) }

    val power get(): Int = results.fold(Color.entries.associateWith { 0 }) { acc, set ->
        acc.mapValues { (color, v) ->
            max(set.scores.singleOrNull { it.color == color }?.score ?: 0, v)
        }
    }.values.fold(1) { acc, res -> acc * res}

    companion object {
        private fun loadGame(input: String): List<Set> = input.split("; ").map(Set::loadSet)
    }
}

val LIMITS = mapOf(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)

fun solve(solver: (List<String>) -> Int, input: String): Int = solver(input.split("\n"))
fun solve(input: List<String>): Int {
    val solver = input.map { line ->
        Game(
            line.drop(5).takeWhile(Char::isDigit).toInt(),
            line.drop(5).dropWhile { c -> c.isDigit() }.dropWhile { !it.isDigit() }
        )
    }
    return solver.sumOf { game ->
        if (game.anySetExceedsLimit(LIMITS)) {
            0
        } else {
            game.id
        }
    }
}

fun solve2(input: List<String>): Int {
    val solver = input.map { line ->
        Game(
            line.drop(5).takeWhile(Char::isDigit).toInt(),
            line.drop(5).dropWhile { c -> c.isDigit() }.dropWhile { !it.isDigit() }
        )
    }
    return solver.sumOf(Game::power)
}

fun main() {
    val input =
        File("/home/timj/docs/repo/GitHub/tim-jacobson/advent-of-code-2023/src/main/resources/day02/input").readLines()
    solve2(input).also { println(it) }
}