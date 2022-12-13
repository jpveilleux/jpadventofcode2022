package day11

import utils.Setup

val daySetup = Setup(11)

data class Player (var worryLevel: Int)

class Monkey (
    var itemsList: MutableList<Int> = mutableListOf(),
    var operation: String = "",
    var test: Int = 0,
    var nextMonkey: MutableList<Int> = mutableListOf(),
    var inspectedItems: Int = 0
) {
    fun getNextMonkeyId(playerWorryLevel: Int): Int {
        if (playerWorryLevel % test == 0) {
            return nextMonkey[0]
        }
        
        return nextMonkey[1]
    }
    
    fun readOperation (player: Player): Int {
        val splitOperation = operation.split(" ")
        val firstOperand = splitOperation[0]
        val operator = splitOperation[1]
        val secondOperand = splitOperation[2]
        val playerWorryLevel = player.worryLevel
    
        val a = when (firstOperand) {
            "old" -> playerWorryLevel
            else -> firstOperand.toInt()
        }
        val b = when (secondOperand) {
            "old" -> playerWorryLevel
            else -> secondOperand.toInt()
        }
        
        return when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            else -> a / b
        }
    }
    
    fun inspectNextItem(player: Player, listOfMonkeys: MutableList<Monkey>) {
        for (i in 0 until itemsList.size) {
            var currentItem = itemsList[i]
            player.worryLevel = currentItem
            
            player.worryLevel = readOperation(player)
            player.worryLevel = player.worryLevel.floorDiv(3)
            
            currentItem = player.worryLevel
    
            val nextMonkeyId = getNextMonkeyId(player.worryLevel)
            
            listOfMonkeys[nextMonkeyId].itemsList.add(currentItem)
            
            //println("player worry level after operation: ${player.worryLevel}")
        }
        inspectedItems += itemsList.size
        itemsList.clear()
    }
    
    override fun toString(): String {
        return "Items: $itemsList \n" +
                "Operation: $operation \n" +
                "Test: $test \n" +
                "Next Monkey: $nextMonkey \n" +
                "Inspected items: $inspectedItems \n"
    }
}

fun getMonkeys(monkeysInfo: MutableList<MutableList<String>>): MutableList<Monkey> {
    val listOfMonkeys = mutableListOf<Monkey>()
    
    monkeysInfo.map {line ->
        val currentMonkey = Monkey()

        line.map{subLine ->
            if(subLine.startsWith("Starting")) {
                currentMonkey.itemsList = subLine.split(":")[1].trim().split(",").map {it.trim().toInt()}.toMutableList()
            } else if (subLine.startsWith("Operation")) {
                currentMonkey.operation = subLine.split("=")[1].trim()
            } else if (subLine.startsWith("Test")) {
                val testLine = subLine.split(" ")
                currentMonkey.test = testLine[testLine.lastIndex].toInt()
            } else {
                val nextMonkeyLine = subLine.split(" ")
                val nextMonkeyNumber = nextMonkeyLine[nextMonkeyLine.lastIndex].toInt()
                currentMonkey.nextMonkey.add(nextMonkeyNumber)
            }
        }
        
        listOfMonkeys.add(currentMonkey)
    }
    
    return listOfMonkeys
}

fun main () {
    fun part1(input: List<String>) {
        val monkeysInfo = mutableListOf<MutableList<String>>()
        val currentMonkeyInfo = mutableListOf<String>()
        val player = Player(0)
        
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
        
        var monkeys = getMonkeys(monkeysInfo)
        
        repeat(20) {
            monkeys.map {monkey ->
                monkey.inspectNextItem(player, monkeys)
                //println(monkey)
            }
        }
        
        monkeys = monkeys.sortedWith(compareByDescending { it.inspectedItems }).toMutableList()
        
        println(monkeys[0].inspectedItems * monkeys[1].inspectedItems)
        //println(monkeys)
    }
    
    //part1(daySetup.controlInput)
    part1(daySetup.input)
}