package day04

import readInput

fun main() {
    val testInputFileName = "./day04/Day04_test"
    val part1controlFileName = "./day04/Day04_part1_control"
    val inputFileName = "./day04/Day04"

    val part1controlInput = readInput(part1controlFileName)
    val input = readInput(inputFileName)

    fun part1(input: List<String>): Int {
        var nbrFound = 0;

        println("List of pairs that did not contain one or the other:")
        input.map {
            val pairArray = it.split(",")
            val pair1 = pairArray[0].split("-")
            val pair2 = pairArray[1].split("-")

            val pair1Part1 = pair1[0].toInt();
            val pair1Part2 = pair1[1].toInt();

            val pair2Part1 = pair2[0].toInt();
            val pair2Part2 = pair2[1].toInt();

            if (pair1Part1 in pair2Part1..pair2Part2 && pair1Part2 in pair2Part1..pair2Part2) {
                nbrFound++
            } else if (pair2Part1 in pair1Part1..pair1Part2 && pair2Part2 in pair1Part1..pair1Part2) {
                nbrFound++
            } else {
                println(pairArray)
            }
        }

        println("Number of pairs that contained one or the other: $nbrFound")
        println("-------------------------------------------------------------")

        return nbrFound
    }

    fun part2 (input: List<String>): Int {
        var nbrFound = 0;

        println("List of pairs that did not overlap each-other:")
        input.map {
            val pairArray = it.split(",")
            val pair1 = pairArray[0].split("-")
            val pair2 = pairArray[1].split("-")

            val pair1Part1 = pair1[0].toInt();
            val pair1Part2 = pair1[1].toInt();

            val pair2Part1 = pair2[0].toInt();
            val pair2Part2 = pair2[1].toInt();

            if (pair1Part1 in pair2Part1..pair2Part2 || pair1Part2 in pair2Part1..pair2Part2) {
                nbrFound++
            } else if (pair2Part1 in pair1Part1..pair1Part2 || pair2Part2 in pair1Part1..pair1Part2) {
                nbrFound++
            } else {
                println(pairArray);
            }
        }

        println("Number of pairs that contained one or the other: $nbrFound")
        println("-------------------------------------------------------------")

        return nbrFound
    }

    check(part1(part1controlInput) == 2)
    check(part2(part1controlInput) == 4)
    //part2(part1controlInput);

    part1(input)
    part2(input)
}