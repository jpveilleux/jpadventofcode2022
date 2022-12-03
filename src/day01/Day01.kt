package day01

import readInput

fun main() {
    val testInputFileName = "Day01_test";
    val inputFileName = "Day01";
    val testInput = readInput(testInputFileName)
    val input = readInput(inputFileName)

    fun part1(input: List<String>, nameOfFile: String) {
        getMeTheFuckingHighestCalCount(input, "$nameOfFile.txt");
    }

    part1(input, inputFileName);
    part1(testInput, testInputFileName);

    fun part2(input: List<String>) {
        val calTotals = getListOfFuckingCaloriesTotals(input);
        val total = calTotals.sortedDescending().slice(0..2).sum();

        println("Top three elves' total calories: $total")
    }

    part2(input);
}

private fun getMeTheFuckingHighestCalCount(input: List<String>, nameOfFile: String) {
    var currentCalCount = 0;
    var currentHighestCalCount = 0;

    for (line in input) {
        if (line.isNotEmpty()) {
            currentCalCount += line.toInt();
        } else {
            if (currentHighestCalCount < currentCalCount) {
                currentHighestCalCount = currentCalCount;
            }

            currentCalCount = 0;
        }
    }

    println("The highest calories count in \"$nameOfFile\" is $currentHighestCalCount");
}

private fun getListOfFuckingCaloriesTotals(input: List<String>): List<Int> {
    val allTotals = arrayListOf<Int>();
    var currentCalCount = 0;

    for (line in input) {
        if (line.isNotEmpty()) {
            currentCalCount += line.toInt();
        } else {
            allTotals.add(currentCalCount);
            currentCalCount = 0;
        }
    }

    return allTotals;
}
