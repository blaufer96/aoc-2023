import kotlin.math.pow

data class Card(
    val id: Int,
    val numbers: Set<String>,
    val winningNumbers: Set<String>,
    var count: Int = 1
)

private fun parseInput(input: String): Card {
  val id = input.substringBefore(":").substringAfter(" ").trim().toInt()
  val winningNumbers = input.substringAfter(":").substringBefore("|").numbersToSet()
  val numbers = input.substringAfter(":").substringAfter("|").numbersToSet()
  return Card(id, numbers, winningNumbers)
}

fun String.numbersToSet() = trim().split("\\s+".toRegex()).toSet()

fun main() {
  fun part1(input: List<String>): Int {
    val cards: List<Card> = input.map { parseInput(it) }
    return cards.map { it.numbers intersect it.winningNumbers }.sumOf {
      2F.pow(it.size - 1).toInt()
    }
  }

  fun part2(input: List<String>): Int {
    val cards: List<Card> = input.map { parseInput(it) }
    for (card in cards) {
      val numbersCount = (card.numbers intersect card.winningNumbers).size
      for (i in 1..numbersCount) cards.find { it.id == card.id + i }!!.count += card.count
    }
    return cards.sumOf { it.count }
  }

  val testInput = readInput("day04/test")
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day04/input")
  part1(input).println()
  part2(input).println()
}
