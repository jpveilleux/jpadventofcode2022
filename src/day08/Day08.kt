package day08

import readInput

// ##########################################################################
//   Start of Setup  ########################################################

const val currentDay = 8
const val baseDir = "./day0${currentDay}/"
const val testInputFileName = "${baseDir}Day0${currentDay}_test"
const val part1controlFileName = "${baseDir}Day0${currentDay}_part1_control"
const val part2controlFileName = "${baseDir}Day0${currentDay}_part2_control"
const val inputFileName = "${baseDir}Day0${currentDay}"

val part1controlInput = readInput(part1controlFileName)
val part2controlInput = readInput(part2controlFileName)
val input = readInput(inputFileName)

//   End of Setup  ##########################################################
// ##########################################################################

// TODO: Adjust X and Y in Tree class using constructor
// TODO: Finish Implementing the getTreesToTheNorth() and
// TODO: -- getTreesToTheSouth() functions
// TODO: Implement function that checks from tree in all
// TODO: -- directions and returns TRUE is one of them
// TODO: -- is not hidden from edge
// TODO: -- Sum the trees visible from edge through var


class Tree (
    val x: Int,
    val y: Int,
    val height: Int
) {
    fun getTreesToTheWest(forest: MutableList<MutableList<Tree>>): MutableList<Tree> {
        val treesToTheWest = mutableListOf<Tree>()
        
        println("Looking west")
        for (i in x - 1 downTo  0) {
            // println(forest[y][i])
            treesToTheWest.add(forest[y][i])
        }
        
        return treesToTheWest
    }
    
    fun getTreesToTheEast(forest: MutableList<MutableList<Tree>>): MutableList<Tree> {
        val treesToTheEast = mutableListOf<Tree>()
        
        println("Looking east")
        for (i in x + 1 until forest[y].lastIndex + 1) {
            // println(forest[y][i])
            treesToTheEast.add(forest[y][i])
        }
        
        return treesToTheEast
    }
    
    fun getTreesToTheNorth(forest: MutableList<MutableList<Tree>>) {
        println("Looking north")
    }
    
    fun getTreesToTheSouth(forest: MutableList<MutableList<Tree>>) {
        println("Looking south")
    }
    
    override fun toString(): String {
        return "h:$height:x$x:y$y"
    }
}

fun getTreeAt(x: Int, y: Int, forest: MutableList<MutableList<Tree>>): Tree {
    return forest[y][x]
}

fun main () {
    fun part1 (input: List<String>) {
        var forestWidth = 0
        var forestLength = 0
        val forest = mutableListOf<MutableList<Tree>>()
        
        for (i in input.indices) {
            // --  Rows  -------------------
            
            val lineList = input[i].split("").drop(1).dropLast(1)
            // println("linelist split $lineList")
            
            val rowTreeHeights = mutableListOf<Int>()
            
            for (f in lineList.indices) {
                val treeHeight = lineList[f].toInt()
                rowTreeHeights.add(treeHeight)
            }
            
            forestWidth = rowTreeHeights.size
            forestLength++
    
            val rowOfTrees = mutableListOf<Tree>()
            
            for (j in rowTreeHeights.indices) {
                // --  Columns  -------------------
                
                rowOfTrees += Tree(j, i, rowTreeHeights[j])
            }
            
            forest.add(rowOfTrees)
    
            //println(input[i])
        }
        
        println("forestWidth $forestWidth")
        println("forestLength $forestLength")
        println("forest: $forest with ${forest.size} items")
        
        val testTree = getTreeAt(42, 8, forest)
        
        //testTree.getTreesToTheWest(forest)
        //testTree.getTreesToTheEast(forest)
        
        println("testTree: $testTree")
    }
    
    fun part2 (input: List<String>) {
    
    }
    
    part1(input)
}

