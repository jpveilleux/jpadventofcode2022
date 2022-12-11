package day10

import readInput
import utils.Setup

val daySetup = Setup(10)

data class SignalStrength (var signalStrength: Int)
class Sprite (var position: List<Int>) {
    fun getCenter (): Int {
        if (position.size > 3) {
            return position[0]
        } else {
            return position[1]
        }
    }
}

fun main () {
    fun cycleModTo20(multiple: Int, cycles: Int): Boolean {
        return cycles % multiple == 20
    }
    fun updateX(cycles: Int, x: Int, sigStrObject: SignalStrength) {
        if (cycles >= 20 && cycleModTo20(40, cycles)) {
            sigStrObject.signalStrength += (x * cycles)
        }
    }
    
    fun cycleIsDivisibleBy(multiple: Int, cycles: Int): Boolean {
        return cycles % multiple == 0
    }
    
    fun updateSpritePos(x: Int, sprite: Sprite) {
        sprite.position = listOf(x - 1, x, x + 1)
    }
    
    fun getPixelToDraw(currentScreenLine: MutableList<String>, sprite: Sprite): String {
        var currentPixelPos = currentScreenLine.size
        
        if (currentPixelPos in sprite.position) {
            return "#"
        }
        
        return "."
    }
    
    fun updateScreen(
        cycles: Int,
        x: Int,
        screenLines: MutableList<MutableList<String>>,
        currentScreenLine: MutableList<String>,
        sprite: Sprite
    ) {
        if (cycles > 20 && cycleIsDivisibleBy(40, cycles)) {
            println("updateScreen cycles: $cycles")
            
            currentScreenLine.add(getPixelToDraw(currentScreenLine, sprite))
            val finishedScreenLine = currentScreenLine.toMutableList()
            
            screenLines.add(finishedScreenLine)
            currentScreenLine.clear()
        } else {
            currentScreenLine.add(getPixelToDraw(currentScreenLine, sprite))
        }
    }
    
    fun part1 (input: List<String>): Int {
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
        
        return signalStr.signalStrength
    }
    
    fun part2 (input: List<String>) {
        var x = 1
        var cmdNbr = 0
        var cycles = 0
        val signalStr = SignalStrength(0)
        val sprite = Sprite(listOf(0,1,2))
    
        val screenLines = mutableListOf<MutableList<String>>()
        val currentScreenLine = mutableListOf<String>()
    
        input.map {line ->
            cmdNbr++
        
            val splitCmd = line.split(" ")
        
            if (splitCmd.size > 1) {
                val cmd = splitCmd[0]
                val changeAmount = splitCmd[1].toInt()
            
                for (cycle in 1..2) {
                    cycles++
                    
                    if (cycle == 2) {
                        x += changeAmount
                        updateSpritePos(x, sprite)
                    }
                    
                    updateScreen(cycles, x, screenLines, currentScreenLine, sprite)
                }
            } else {
                cycles++
                updateScreen(cycles, x, screenLines, currentScreenLine, sprite)
            }
        }
    
        println("Total cycles: $cycles")
        println("Total x: $x")
        println("screen lines: $screenLines")
    
        screenLines.map {line ->
            val lineToPrint = line.joinToString("")
            println(lineToPrint)
        }
    }
    
    check(part1(daySetup.controlInput) == 13140)
    check(part1(daySetup.input) == 16020)
    
    part2(daySetup.controlInput)
    //part2(input)
}