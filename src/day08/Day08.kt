data class MapInstructions(val instructions: String, val map: Map<String, Pair<String, String>>) {
  fun countSteps(start: String): Int {
    var steps = 0
    var current = start
    while (!current.endsWith("Z")) {
      val currentVal = map.getValue(current)
      val instruction = instructions[steps++ % instructions.length]
      current = if (instruction == 'L') currentVal.first else currentVal.second
    }
    return steps
  }
}

private fun parseInput(input: List<String>): MapInstructions {
  val instructions = input.first()
  val map =
      input.drop(2).associate {
        it.substring(0..2) to Pair(it.substring(7..9), it.substring(12..14))
      }
  return MapInstructions(instructions, map)
}

fun Long.gcd(n: Long): Long {
  var result = 1L
  var min = minOf(this, n)
  for (i in 1L..min) {
    if (this % i == 0L && n % i == 0L) result = i
  }
  return result
}

fun Long.lcm(n: Long): Long = this * n / this.gcd(n)

fun main() {
  fun part1(input: List<String>): Int {
    val mapInstructions = parseInput(input)
    return mapInstructions.countSteps("AAA")
  }

  fun part2(input: List<String>): Long {
    val mapInstructions = parseInput(input)
    return parseInput(input)
        .map
        .keys
        .filter { it.endsWith("A") }
        .map { mapInstructions.countSteps(it).toLong() }
        .reduce { prev, next -> prev.lcm(next) }
  }

  val testInput = readInput("day08/test")
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day08/input")
  part1(input).println()
  part2(input).println()
}
