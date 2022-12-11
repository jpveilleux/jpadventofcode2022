package day10

import readInput

// ##########################################################################
//   Start of Setup  ########################################################

const val currentDay = 10

const val baseDir = "./day${currentDay}/"
const val testInputFileName = "${baseDir}Day${currentDay}_test"
const val part1controlFileName = "${baseDir}Day${currentDay}_part1_control"
const val part2controlFileName = "${baseDir}Day${currentDay}_part2_control"
const val inputFileName = "${baseDir}Day0${currentDay}"

val part1controlInput = readInput(part1controlFileName)
val part2controlInput = readInput(part2controlFileName)
val input = readInput(inputFileName)

//   End of Setup  ##########################################################
// ##########################################################################

// "addx V" = 2 cycles
// - add/subtract number (V) after the two cycles
// "noop" = 1 cycle
// - Has no effect


data class SignalStrength (var signalStrength: Int)


fun main () {
    
    fun updateX(cycles: Int, x: Int, sigStrObject: SignalStrength) {
        println(cycles)
        
        if (cycles >= 20 && (cycles % 40 == 20)) {
            println("x: $x")
            println("stating 20 then every 40: $cycles")
            sigStrObject.signalStrength += (x * cycles)
        }
    }
    
    fun part1 (input: List<String>) {
        var x = 1
        var cmdNbr = 0
        var cycles = 0
        val signalStr = SignalStrength(0)
        
        input.map {line ->
            cmdNbr++
            
            val splitCmd = line.split(" ")
            
            if (splitCmd.size > 1) {
                val cmd = splitCmd[0]
                val changeAmount = splitCmd[1].toInt()
                
                for (cycle in 1..2) {
                    cycles++
                    updateX(cycles, x, signalStr)
                    
                    if (cycle == 2) {
                        x += changeAmount
                    }
                }
            } else {
                cycles++
                updateX(cycles, x, signalStr)
            }
        }
    
        println("cycles: $cycles")
        println("X: $x")
        println("Final signal strength: ${signalStr.signalStrength}")
    }
    
    fun part2 (input: List<String>) {
    
    }
    
    //part1(part1controlInput)
    part1(input)
}