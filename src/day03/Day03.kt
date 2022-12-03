package day03

import readInput

fun main () {
    val testInputFileName = "./day03/Day03_test"
    val controlInputFileName = "./day03/Day03_part2_control"
    val inputFileName = "./day03/Day03"
    val testInput = readInput(testInputFileName)
    val controlInput = readInput(controlInputFileName)
    val input = readInput(inputFileName)

    val alpha = arrayListOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    fun getCharValue(char: Char): Int {
        if (char.isUpperCase()) {
            return alpha.indexOf(char.lowercaseChar()) + (1 + alpha.size)
        }

        return alpha.indexOf(char) + 1
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { it ->
            val ruckSacks = it.chunked(it.length/2);

            val ruckSack1Items = ruckSacks[0].toCharArray();
            val ruckSack2Items = ruckSacks[1].toCharArray();

            val dupRuckItem = ruckSack1Items.find { currentRuckItem ->
                ruckSack2Items.contains(currentRuckItem)
            }

            getCharValue(dupRuckItem!!);
        }
    }

    fun part2(input: List<String>): Int {
        val by3Chunks = input.chunked(3);

        return by3Chunks.sumOf { ruckSackTrio ->
            val ruckSack1Items = ruckSackTrio[0].toCharArray();
            val ruckSack2Items = ruckSackTrio[1].toCharArray();
            val ruckSack3Items = ruckSackTrio[2].toCharArray();

            val dupRuckItem = ruckSack1Items.findLast { currentRuckItem ->
                ruckSack2Items.contains(currentRuckItem) && ruckSack3Items.contains(currentRuckItem)
            }

            getCharValue(dupRuckItem!!);
        }
    }

    // Validation
    check(part1(testInput) == 157);
    check(part2(controlInput) == 70);

    // Debug
    println(part1(input));
    println(part2(input));
}