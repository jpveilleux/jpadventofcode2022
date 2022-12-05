package day04

import readInput

fun main() {
    val testInputFileName = "./day04/Day04_test"
    val part1controlFileName = "./day04/Day04_part1_control"
    val part2controlFileName = "./day04/Day04_part2_control"
    val part1JPcontrolFileName = "./day04/Day04_jp_control"
    val inputFileName = "./day04/Day04"

    val part1controlInput = readInput(part1controlFileName)
    val part1JPcontrolInput = readInput(part1JPcontrolFileName)
    //val part2controlInput = readInput(part2controlFileName)
    val input = readInput(inputFileName)

    fun part1(input: List<String>): Int {
        var nbrFound = 0;

        input.map {
            val pairArray = it.split(",")
            val pair1 = pairArray[0].split("-")
            val pair2 = pairArray[1].split("-")

            if (pair1[0].toInt() >= pair2[0].toInt() && pair1[1].toInt() <= pair2[1].toInt()) {
                nbrFound++
                println("------------------------------")
                println("FP Fits in SP - $pairArray")
                println("------------------------------")
            } else if ((pair1[1].toInt() >= pair2[1].toInt()) && (pair1[0].toInt() <= pair2[0].toInt())) {
                nbrFound++
                println("------------------------------")
                println("SP Fits in FP - $pairArray")
                println("------------------------------")
            } else {
                println(pairArray)
            }
        }

        println(nbrFound)
        return nbrFound
    }

    fun part2 (input: List<String>): Int {
        var nbrFound = 0;

        input.map {
            val pairArray = it.split(",")
            val pair1 = pairArray[0].split("-")
            val pair2 = pairArray[1].split("-")

            if (pair1[0].toInt() >= pair2[0].toInt() || pair1[1].toInt() <= pair2[1].toInt()) {
                nbrFound++
                println("------------------------------")
                println("FP Overlaps SP - $pairArray")
                println("------------------------------")
            } else if ((pair1[1].toInt() >= pair2[1].toInt()) || (pair1[0].toInt() <= pair2[0].toInt())) {
                nbrFound++
                println("------------------------------")
                println("SP Overlaps FP - $pairArray")
                println("------------------------------")
            } else {
                println(pairArray)
            }
        }
        println(nbrFound)
        return nbrFound
    }



    check(part1(part1controlInput) == 2)
    check(part2(part1controlInput) == 4)


    //part1(part1JPcontrolInput)

    part1(input)
    part2(input)
}