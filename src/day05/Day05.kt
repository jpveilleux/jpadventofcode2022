package day05

import readInput

fun main () {
    val currentDay = 5
    val baseDir = "./day0${currentDay}/"
    val testInputFileName = "${baseDir}Day0${currentDay}_test"
    val part1controlFileName = "${baseDir}Day0${currentDay}_part1_control"
    val inputFileName = "${baseDir}Day0${currentDay}"

    val part1controlInput = readInput(part1controlFileName)
    val input = readInput(inputFileName)

    fun getDocks(input: List<String>): MutableList<MutableList<String>> {
        val docks = mutableListOf<MutableList<String>>(
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )

        input.map { line ->
            if (line.contains("[")) {
                val currentLineChunks = line.chunked(4);
                val cleanedChunks = currentLineChunks.map { chunk ->
                    chunk.replace("[", "").replace("]", "").replace(" ", "").trimIndent()
                }

                for (i in cleanedChunks.indices) {
                    if(cleanedChunks[i].isNotEmpty()) {
                        docks[i].add(cleanedChunks[i])
                    }
                }
            }
        }

        return docks
    }

    fun getMoveLines(input: List<String>): List<List<Int>> {
        val moveLines = mutableListOf<MutableList<Int>>()

        input.map {line ->
            if (line.contains("move")) {
                val data = line.split(" ")
                val moveLine = mutableListOf<Int>()

                for (number in 1 until data.size step 2) {
                    moveLine.add(data[number].toInt())
                }

                moveLines.add(moveLine)
            }
        }

        return moveLines
    }

    fun addCrates(crates: MutableList<String>, col: MutableList<String>, shouldReverse: Boolean = false) {
        for (i in 0 until crates.size) {
            if (shouldReverse) {
                col.add(0, crates.reversed()[i])
            } else {
                col.add(0, crates[i])
            }
        }
    }

    fun grabCrates(amount: Int, col: MutableList<String>): MutableList<String> {
        val crates = mutableListOf<String>()

        for (i in 0 until amount) {
            crates.add(col[i])
        }

        return crates
    }

    fun removeCrates(amount: Int, col: MutableList<String>) {
        for (i in 0 until amount) {
            col.removeAt(0)
        }
    }

    fun executeMove(
        amountOfCrates: Int,
        originColNbr: Int,
        destinationColNbr: Int,
        docks: MutableList<MutableList<String>>,
        shouldReverse: Boolean = false
    ) {
        val oriColNbr = originColNbr - 1
        val destColNbr = destinationColNbr - 1

        val movedCrates = grabCrates(amountOfCrates, docks[oriColNbr])
        addCrates(movedCrates, docks[destColNbr], shouldReverse)
        removeCrates(movedCrates.size, docks[oriColNbr])
    }

    fun part1(input: List<String>): String {
        val docks = getDocks(input)

        getMoveLines(input).map {moveLine ->
            val amountOfCrates = moveLine[0]
            val originCol = moveLine[1]
            val destinationCol = moveLine[2]

            executeMove(amountOfCrates, originCol, destinationCol, docks)
        }

        val endString = mutableListOf<String>()

        docks.map {curCol ->
            endString.add(curCol[0]);
        }

        return endString.joinToString("")
    }

    fun part2(input: List<String>): String {
        val docks = getDocks(input)

        getMoveLines(input).map {moveLine ->
            val amountOfCrates = moveLine[0]
            val originCol = moveLine[1]
            val destinationCol = moveLine[2]
            val shouldReverse = true

            executeMove(amountOfCrates, originCol, destinationCol, docks, shouldReverse)
        }

        val endString = mutableListOf<String>()

        docks.map {curCol ->
            endString.add(curCol[0])
        }

        return endString.joinToString("")
    }

    println(part1(input))
    println(part2(input))
}