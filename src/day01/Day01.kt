fun String.modifyText(): List<String> =
    windowed(5, 1, true) {
      when {
        it[0].isDigit() -> it[0].toString()
        it.startsWith("one") -> "1"
        it.startsWith("two") -> "2"
        it.startsWith("three") -> "3"
        it.startsWith("four") -> "4"
        it.startsWith("five") -> "5"
        it.startsWith("six") -> "6"
        it.startsWith("seven") -> "7"
        it.startsWith("eight") -> "8"
        it.startsWith("nine") -> "9"
        else -> ""
      }
    }

fun main() {
  fun part1(input: List<String>): Int =
      input.map { it.filter { it.isDigit() } }.sumOf { "${it.first()}${it.last()}".toInt() }

  fun part2(input: List<String>): Int =
      input.map { it.modifyText() }.map { it.filter { it.isNotBlank() } }.sumOf {
        "${it.first()}${it.last()}".toInt()
      }

  val testInput = readInput("day01/test")
  // part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day01/input")
  part1(input).println()
  part2(input).println()
}
