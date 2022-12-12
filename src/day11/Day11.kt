package day11

import utils.Setup

val daySetup = Setup(11)

data class Player (var worryLevel: Int)

class Monkey (
    val itemsList: MutableList<Int>,
    val operation: String,
    val test: Int,
    val nextMonkey: List<Int>
) {
    fun getNextMonkey(playerWorryLevel: Int): Int {
        if (playerWorryLevel % test == 0) {
            return nextMonkey[0]
        }
        
        return nextMonkey[1]
    }
    
    fun readOperation (playerWorryLevel: Int) {
        val splitOperation = operation.split(" ")
        val firstOperand = splitOperation[0]
        val operator = splitOperation[1]
        val secondOperand = splitOperation[2]
    }
    
    fun inspectNextItem(playerWorryLevel: Int) {
        for (i in 0 until itemsList.size) {
            val currentItem = itemsList[1]
            
            readOperation(playerWorryLevel)
        }
    }
}

fun getMonkeys(monkeysInfo: MutableList<MutableList<String>>) {
    monkeysInfo.map {line ->
        // Put starting items into a list and then set that list to Monkey.itemsList
        // Store Operation to Monkey.operation as a 3 item vector (List<Int>)
        // Take the divisible by number and store it in Monkey.test
        // Take the last two items in "line" and parse the number (lastIndex) and put them in Monkey.nextMonkey
        println(line)
    }
}

fun main () {
    fun part1(input: List<String>) {
        val monkeysInfo = mutableListOf<MutableList<String>>()
        val currentMonkeyInfo = mutableListOf<String>()
        
        val inputCleaned = input.filter {line ->
            line.isNotEmpty() && !line.startsWith("Monkey")
        }
        
        var count = 0;
        
        for (i in inputCleaned.indices) {
            val line = inputCleaned[i]
            count++
            
            if (count == 5) {
                currentMonkeyInfo.add(line.trim())
                
                val monkeyInfoArray = currentMonkeyInfo.toMutableList()
                monkeysInfo.add(monkeyInfoArray)
                
                currentMonkeyInfo.clear()
                
                count = 0
            } else {
                currentMonkeyInfo.add(line.trim())
            }
        }
        
        getMonkeys(monkeysInfo)
        
        //println(monkeysInfo)
    }
    
    part1(daySetup.input)
}