data class Game(val id: Int, val red: Int, val green: Int, val blue: Int) {
  fun isPossible() = if (red <= 12 && green <= 13 && blue <= 14) id else 0

  fun multiply() = red * green * blue
}

fun parseInput(input: String): Game {
  val id = input.substringBefore(":").substringAfter(" ").toInt()
  val colors = mutableMapOf<String, Int>()
  input.substringAfter(": ").split("; ").forEach { turn ->
    turn.split(", ").forEach { draw ->
      val count = draw.substringBefore(" ").toInt()
      val color = draw.substringAfter(" ")
      colors[color] = maxOf(count, colors[color] ?: count)
    }
  }
  return Game(id, colors["red"] ?: 0, colors["green"] ?: 0, colors["blue"] ?: 0)
}

fun main() {
  fun part1(input: List<String>): Int {
    val games: List<Game> = input.map { parseInput(it) }
    return games.sumOf { it.isPossible() }
  }

  fun part2(input: List<String>): Int {
    val games: List<Game> = input.map { parseInput(it) }
    return games.sumOf { it.multiply() }
  }

  val testInput = readInput("day02/test")
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day02/input")
  part1(input).println()
  part2(input).println()
}