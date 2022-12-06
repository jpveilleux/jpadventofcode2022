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

    // TODO: Check to reverse from row based to col based!!!
    // TODO: Create setCol() function
    // TODO: Create cycle code that goes through a column to find next X crates from the beginning
    // TODO: Change moveCrate() function to moveCrates(movedCrate: List<String>, destinationCol: Int)  -  This will use getCol() and setCol()?

    fun getDocks(input: List<String>): MutableList<MutableList<String>> {
        var docks = mutableListOf<MutableList<String>>(
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf()
        )

        var row = 0;
        input.map { line ->
            if (line.contains("[")) {
                val currentLineChunks = line.chunked(4);
                val cleanedChunks = currentLineChunks.map { chunk ->
                    chunk.replace("[", "").replace("]", "").replace(" ", "").trimIndent()
                }

                // Insert the crates in their appropriate spots
                cleanedChunks.map {character ->
                    //println("char: $character - row nbr: $row")
                    docks[row].add(character)
                }

                // Pad the rows so the empty cells are added to array (row)
                if(docks[row].size < 9) {
                    for(i in 0 until  (9 - docks[row].size)) {
                        docks[row].add("")
                    }
                }

                row++
            }
        }

        return docks
    }

    fun getCrate(col: Int, row: Int, docks: MutableList<MutableList<String>>): String {
        return docks[row - 1][col - 1];
    }

    fun moveCrate(oriCol: Int, oriRow: Int, targetCol: Int, targetRow: Int, docks: MutableList<MutableList<String>>) {
        docks[targetRow - 1][targetCol - 1] = docks[oriRow - 1][oriCol - 1];
        docks[oriRow - 1][oriCol - 1] = ""
    }

    fun getCol(colNbr: Int, docks: MutableList<MutableList<String>>): List<String> {
        return docks.map {
            it[colNbr - 1]
        }
    }

    fun part1(input: List<String>): String {
        val docks = getDocks(input)

        println(docks)

//        println("Crate to be moved: ${getCrate(5, 8, docks)}")
//        println("Spot where crate is to be moved: ${getCrate(3, 3, docks)}")
//        moveCrate(5, 8, 3, 3, docks);
//        println("Crate to be moved (after): ${getCrate(5, 8, docks)}")
//        println("Spot where crate was moved (after): ${getCrate(3, 3, docks)}")
        println("Col 7: ${getCol(7, docks)}")

        //println(docks)
        return ""
    }

    fun part2(input: List<String>): String {
        // Part 2 code
        return ""
    }

    part1(input);
}