data class Coords(val x: Int, val y: Int) {
  fun getNeighbors(): Set<Coords> {
    val set = mutableSetOf<Coords>()
    for (i in x - 1..x + 1) {
      for (j in y - 1..y + 1) set.add(Coords(i, j))
    }
    return set
  }
}

class PartNumber {
  val number = mutableListOf<Char>()
  val locations = mutableSetOf<Coords>()

  fun add(ch: Char, location: Coords) {
    number.add(ch)
    locations.addAll(location.getNeighbors())
  }

  fun toInt() = number.joinToString("").toInt()
}

private fun parseInput(
    input: List<String>,
    isGear: Boolean = false
): Pair<Set<PartNumber>, Set<Coords>> {
  val numbers = mutableSetOf<PartNumber>()
  val symbols = mutableSetOf<Coords>()
  var currentNumber = PartNumber()

  input.forEachIndexed { y, row ->
    row.forEachIndexed { x, ch ->
      if (ch.isDigit()) currentNumber.add(ch, Coords(x, y))
      else {
        if (currentNumber.number.isNotEmpty()) {
          numbers.add(currentNumber)
          currentNumber = PartNumber()
        }
        if (!isGear && ch != '.') symbols.add(Coords(x, y))
        if (isGear && ch == '*') symbols.add(Coords(x, y))
      }
    }
    if (currentNumber.number.isNotEmpty()) {
      numbers.add(currentNumber)
      currentNumber = PartNumber()
    }
  }
  return numbers to symbols
}

fun main() {
  fun part1(input: List<String>): Int {
    val (numbers, symbols) = parseInput(input)
    return numbers.filter { it.locations.intersect(symbols).isNotEmpty() }.sumOf { it.toInt() }
  }

  fun part2(input: List<String>): Int {
    val (numbers, symbols) = parseInput(input, true)
    return symbols.sumOf { symbol ->
      val neighbors = numbers.filter { symbol in it.locations }
      if (neighbors.size == 2) neighbors[0].toInt() * neighbors[1].toInt() else 0
    }
  }

  val testInput = readInput("day03/test")
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day03/input")
  part1(input).println()
  part2(input).println()
}
