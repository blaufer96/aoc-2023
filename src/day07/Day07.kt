enum class Type {
  FIVE,
  FOUR,
  FULL,
  THREE,
  PAIRS,
  PAIR,
  HIGH
}

class Hand(val cards: String, val bid: Int, val withJokers: Boolean = false) : Comparable<Hand> {
  val type = cards.toType(withJokers)
  val score = cards.score(order = if (!withJokers) ORDER else JOKER_ORDER)

  override fun compareTo(other: Hand): Int =
      if (type != other.type) (other.type.ordinal - type.ordinal) else (score - other.score).toInt()

  companion object {
    private const val ORDER = "23456789TJQKA"
    private const val JOKER_ORDER = "J23456789TQKA"

    private fun String.toCardCount(): List<Int> =
        groupingBy { it }.eachCount().values.sortedDescending()

    private fun String.toCategory(jokers: Boolean): List<Int> {
      if (!jokers) return toCardCount()
      else {
        val cardsWithoutJokers = filter { it != 'J' }
        val jokersCount = count { it == 'J' }
        return if (jokersCount == 5) listOf(5)
        else cardsWithoutJokers.toCardCount().toMutableList().apply { this[0] += jokersCount }
      }
    }

    private fun String.toType(jokers: Boolean): Type {
      val category = toCategory(jokers)
      return when {
        5 in category -> Type.FIVE
        4 in category -> Type.FOUR
        3 in category && 2 in category -> Type.FULL
        3 in category -> Type.THREE
        category.count { it == 2 } == 2 -> Type.PAIRS
        2 in category -> Type.PAIR
        else -> Type.HIGH
      }
    }

    private fun String.score(order: String): Long =
        fold(1L) { score, char -> score * 13L + order.indexOf(char) }
  }
}

fun List<Hand>.totalWinnings(): Long =
    sorted().foldIndexed(0L) { id, acc, cards -> (id + 1) * cards.bid + acc }

private fun parseInput(input: List<String>, withJokers: Boolean = false): List<Hand> =
    input.map { it.split(" ") }.map { parts ->
      Hand(parts.first(), parts.last().toInt(), withJokers)
    }

fun main() {
  fun part1(input: List<String>): Long = parseInput(input).totalWinnings()

  fun part2(input: List<String>): Long = parseInput(input, true).totalWinnings()

  val testInput = readInput("day07/test")
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day07/input")
  part1(input).println()
  part2(input).println()
}
