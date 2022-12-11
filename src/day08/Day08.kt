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

class Tree (
    val x: Int,
    val y: Int,
    val height: Int,
    var scenicScoreString: String = ""
) {
    fun isVisibleFromTheWest(forest: MutableList<MutableList<Tree>>): Boolean {
        for (i in x - 1 downTo  0) {
            if(forest[y][i].height >= height) {
                return false
            }
        }
        
        return true
    }
    
    fun nbrOfTreesVisibleToTheWest(forest: MutableList<MutableList<Tree>>): Int {
        var treesVisible = 0
        
        for (i in x - 1 downTo  0) {
            treesVisible++

            if(forest[y][i].height >= height){
                break
            }
        }
        
        return treesVisible
    }
    
    fun isVisibleFromTheEast(forest: MutableList<MutableList<Tree>>): Boolean {
        for (i in x + 1 until forest[y].lastIndex + 1) {
            if(forest[y][i].height >= height) {
                return false
            }
        }
    
        return true
    }
    
    fun nbrOfTreesVisibleToTheEast(forest: MutableList<MutableList<Tree>>): Int {
        var treesVisible = 0
        
        for (i in x + 1 until forest[y].lastIndex + 1) {
            treesVisible++

            if(forest[y][i].height >= height){
                break
            }
        }
        
        return treesVisible
    }
    
    fun isVisibleFromTheNorth(forest: MutableList<MutableList<Tree>>): Boolean {
        for (i in y - 1 downTo 0) {
            if(forest[i][x].height >= height) {
                return false
            }
        }
        
        return true
    }
    
    fun nbrOfTreesVisibleToTheNorth(forest: MutableList<MutableList<Tree>>): Int {
        var treesVisible = 0
        
        for (i in y - 1 downTo 0) {
            treesVisible++

            if(forest[i][x].height >= height){
                break
            }
        }
        
        return treesVisible
    }
    
    fun isVisibleFromTheSouth(forest: MutableList<MutableList<Tree>>): Boolean {
        for (i in y + 1 until forest.lastIndex + 1) {
            if(forest[i][x].height >= height) {
                return false
            }
        }
    
        return true
    }
    
    fun nbrOfTreesVisibleToTheSouth(forest: MutableList<MutableList<Tree>>): Int {
        var treesVisible = 0
        
        for (i in y + 1 until forest.lastIndex + 1) {
            treesVisible++
            
            if(forest[i][x].height >= height){
                break
            }
        }
        
        return treesVisible
    }
    
    fun getScenicScore(forest: MutableList<MutableList<Tree>>): Int {
        val westScore = nbrOfTreesVisibleToTheWest(forest)
        val northScore = nbrOfTreesVisibleToTheNorth(forest)
        val eastScore = nbrOfTreesVisibleToTheEast(forest)
        val southScore = nbrOfTreesVisibleToTheSouth(forest)
        
        scenicScoreString = "n:$northScore * w:$westScore * s:$southScore * e:$eastScore"
        
        return westScore * northScore * eastScore * southScore
    }
    
    override fun toString(): String {
        return "x$x:y${y}h:$height"
    }
}

fun getTreeAt(x: Int, y: Int, forest: MutableList<MutableList<Tree>>): Tree {
    return forest[y][x]
}

fun main () {
    fun getForest(input: List<String>): MutableList<MutableList<Tree>> {
        val forest = mutableListOf<MutableList<Tree>>()
    
        for (i in input.indices) {
            // --  Rows  -------------------
        
            val lineList = input[i].split("").drop(1).dropLast(1)
        
            val rowTreeHeights = mutableListOf<Int>()
        
            for (f in lineList.indices) {
                val treeHeight = lineList[f].toInt()
                rowTreeHeights.add(treeHeight)
            }
        
            val rowOfTrees = mutableListOf<Tree>()
        
            for (j in rowTreeHeights.indices) {
                // --  Columns  -------------------
            
                rowOfTrees += Tree(j, i, rowTreeHeights[j])
            }
        
            forest.add(rowOfTrees)
        }
        return forest
    }
    
    fun part1 (input: List<String>): Int {
        val forest = getForest(input)
        
        val treesVisibleFromEdge = mutableListOf<Tree>()
        
        forest.map { treeRow ->
            treeRow.map {tree ->
                if (tree.isVisibleFromTheWest(forest)
                    || tree.isVisibleFromTheNorth(forest)
                    || tree.isVisibleFromTheEast(forest)
                    || tree.isVisibleFromTheSouth(forest)
                ) {
                    treesVisibleFromEdge.add(tree)
                }
            }
        }
        
        return treesVisibleFromEdge.size
    }
    
    fun part2 (input: List<String>): Int {
        val forest = getForest(input)
        
        var bestScenicScore = 0
    
        forest.map { treeRow ->
            treeRow.map {tree ->
                val treeScenicScore = tree.getScenicScore(forest)
                
                println("Tree: $tree, scenicString: ${tree.scenicScoreString}")
                println("Current tree scenic Score: $treeScenicScore")
                println("Scenic Score: $bestScenicScore")
                println("--------------------------------")

                if (treeScenicScore > bestScenicScore) {
                    bestScenicScore = treeScenicScore
                }
            }
        }
        
        return bestScenicScore
    }
    
    //println("Trees visible from the edge (CONTROL): ${part1(part1controlInput)}")
    //println("Trees visible from the edge: ${part1(input)}")
    
    println("Best Scenic Score (CONTROL): ${part2(part1controlInput)}")
    //println("Best Scenic Score: ${part2(input)}")
}

