import java.io.File

data class Converter(val destinationRange: LongRange, val sourceRange: LongRange) {
  fun mapSeed(num: Long): Long = destinationRange.first + (num - sourceRange.first)

  companion object {
    fun of(row: String): Converter {
      val data = row.split(" ").map { it.toLong() }
      return Converter(data[0]..(data[0] + data[2]), data[1]..(data[1] + data[2]))
    }
  }
}

fun parseSeeds(input: String): MutableList<Long> =
    input.substringAfter(": ").split(" ").map { it.toLong() }.toMutableList()

fun parseSeedsRanges(input: String): MutableList<Long> {
  val seedsWithRanges = input.substringAfter(": ").split(" ").map { it.toLong() }
  return seedsWithRanges
      .chunked(2)
      .map { (it.first() ..< (it.first() + it.last())).toList() }
      .flatten()
      .toMutableList()
}

private fun parseInput(input: List<String>): List<List<Converter>> =
    input.map { it.split("\n").drop(1).map { Converter.of(it) } }

fun findLowestNumber(seeds: MutableList<Long>, input: List<String>): Long {
  val convertersLists = parseInput(input.drop(1))
  convertersLists.forEach { list ->
    seeds.forEachIndexed { i, seed ->
      for (converter in list) {
        if (seed in converter.sourceRange) {
          seeds[i] = converter.mapSeed(seed)
          break
        }
      }
    }
  }
  return seeds.minOf { it }
}

fun main() {
  fun part1(input: List<String>): Long {
    val seeds = parseSeeds(input.first())
    return findLowestNumber(seeds, input)
  }

  fun part2(input: List<String>): Long {
    val seeds = parseSeedsRanges(input.first())
    return findLowestNumber(seeds, input)
  }

  val testInput = File("src/day05/test.txt").readText().split("\n\n")
  part1(testInput).println()
  part2(testInput).println()

  val input = File("src/day05/input.txt").readText().split("\n\n")
  part1(input).println()
  part2(input).println()
}
