package utils

import readInput

class Setup (dayNumber: Int) {
    private var baseDir: String = "./day${dayNumber}/"
    private var controlInputFileName: String = "${baseDir}Day${dayNumber}_part1_control"
    private var inputFileName: String = "${baseDir}Day0${dayNumber}"
    val controlInput = readInput(controlInputFileName)
    val input = readInput(inputFileName)
}