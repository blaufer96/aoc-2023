fun extrapolate(row: List<Int>, back: Boolean = false): Int {
  if (row.all { it == 0 }) return 0
  else {
    val newRow = row.windowed(2).map { it[1] - it[0] }
    return if (!back) row.last() + extrapolate(newRow) else row.first() - extrapolate(newRow, true)
  }
}

fun String.toRows(): List<Int> = split(" ").map(String::toInt)

fun main() {
  fun part1(input: List<List<Int>>): Int = input.sumOf { extrapolate(it) }

  fun part2(input: List<List<Int>>): Int = input.sumOf { extrapolate(it, true) }

  val testInput = readInput("day09/test").map(String::toRows)
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day09/input").map(String::toRows)
  part1(input).println()
  part2(input).println()
}
