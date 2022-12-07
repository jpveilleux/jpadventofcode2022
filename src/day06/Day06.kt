package day06

import readInput

const val currentDay = 6
const val baseDir = "./day0${currentDay}/"
const val testInputFileName = "${baseDir}Day0${currentDay}_test"
const val part1controlFileName = "${baseDir}Day0${currentDay}_part1_control"
const val part2controlFileName = "${baseDir}Day0${currentDay}_part2_control"
const val inputFileName = "${baseDir}Day0${currentDay}"

val part1controlInput = readInput(part1controlFileName)
val part2controlInput = readInput(part2controlFileName)
val input = readInput(inputFileName)

fun main() {
    fun part1(input: List<String>): Int {
        val chars = input[0].toCharArray()
        var buffer = arrayListOf<Char>()

        for (i in chars.indices) {
            println(buffer.distinct().count() == 4)

            if(buffer.distinct().count() < 4) {
                if(buffer.size == 4) {
                    buffer.removeAt(0)
                }

                buffer.add(chars[i])
            } else {
                println(chars[i])
                return i
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val chars = input[0].toCharArray()
        var buffer = arrayListOf<Char>()

        for (i in chars.indices) {
            println(buffer.distinct().count() == 14)

            if(buffer.distinct().count() < 14) {
                if(buffer.size == 14) {
                    buffer.removeAt(0)
                }

                buffer.add(chars[i])
            } else {
                println(chars[i])
                return i
            }
        }

        return 0
    }

    println(part1(input))
    println(part2(input))
}



