package utils

import readInput

class Setup (dayNumber: Int) {
    private var baseDir: String = "./day${dayNumber}/"
    private var controlInputFileName: String = "${baseDir}Day${dayNumber}_control"
    private var inputFileName: String = "${baseDir}Day${dayNumber}"
    val controlInput = readInput(controlInputFileName)
    val input = readInput(inputFileName)
}