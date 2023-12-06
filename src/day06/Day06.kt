data class Race(val time: Long, val distance: Long) {
  fun calculate(): Int = (1..time).count { it * (time - it) > distance }
}

fun parseRace(input: List<String>): Race {
  val (times, distances) = input.map { it.split(" ").filter { it.isNotBlank() }.drop(1) }
  return Race(times.joinToString("").toLong(), distances.joinToString("").toLong())
}

fun main() {
  fun part1(input: List<String>): Int {
    val (times, distances) = input.map { it.split(" ").filter { it.isNotBlank() }.drop(1) }
    val racesList = times.zip(distances).map { (t, d) -> Race(t.toLong(), d.toLong()) }
    return racesList.map { it.calculate() }.reduce { a, b -> a * b }
  }

  fun part2(input: List<String>): Int {
    val (times, distances) = input.map { it.split(" ").filter { it.isNotBlank() }.drop(1) }
    val race = Race(times.joinToString("").toLong(), distances.joinToString("").toLong())
    return race.calculate()
  }

  val testInput = readInput("day06/test")
  part1(testInput).println()
  part2(testInput).println()

  val input = readInput("day06/input")
  part1(input).println()
  part2(input).println()
}
