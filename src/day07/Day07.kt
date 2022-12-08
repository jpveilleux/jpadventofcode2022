package day07

import readInput
import java.lang.NumberFormatException

const val currentDay = 7
const val baseDir = "./day0${currentDay}/"
const val testInputFileName = "${baseDir}Day0${currentDay}_test"
const val part1controlFileName = "${baseDir}Day0${currentDay}_part1_control"
const val part2controlFileName = "${baseDir}Day0${currentDay}_part2_control"
const val inputFileName = "${baseDir}Day0${currentDay}"

val part1controlInput = readInput(part1controlFileName)
val part2controlInput = readInput(part2controlFileName)
val input = readInput(inputFileName)

class BreadCrumbs () {
    var deepestPath: MutableList<String> = mutableListOf()
    var fullPath: MutableList<String> = mutableListOf()
    
    fun getClonedFullPath (leafNameToBeAppended: String = ""): MutableList<String> {
        val newFullPath = fullPath.toMutableList()
        
        if(leafNameToBeAppended.isNotEmpty()) {
            newFullPath.add(leafNameToBeAppended)
        }
        
        return newFullPath
    }
    
    fun getParentLeafFullPath (): MutableList<String> {
        val parentLeafFullPath = fullPath.toMutableList()
        
        // if(parentLeafFullPath.size > 1) {
        //     parentLeafFullPath.removeAt(parentLeafFullPath.lastIndex)
        // }
        
        return parentLeafFullPath
    }
}

interface ILeaf {
    val name: String
    val leafSize: Int
    val type: String
    val parentLeafFullPath: MutableList<String>
    val fullPath: MutableList<String>
    val childLeaves: MutableList<Leaf>
}

class Leaf(
    override val name: String,
    override var leafSize: Int = 0,
    override val type: String = "folder",
    override val fullPath: MutableList<String> = mutableListOf(),
    override val parentLeafFullPath: MutableList<String> = mutableListOf(),
    override val childLeaves: MutableList<Leaf> = mutableListOf()
) : ILeaf {
    override fun toString (): String {
        return "\n" +
                "\nName: $name" +
                "\nLeaf size: $leafSize" +
                "\nType: $type" +
                "\nFull Path: $fullPath" +
                "\nUnique Identifier: \"${getUniqueIdentifier()}\"" +
                "\nParent Leaf Path: $parentLeafFullPath" +
                "\nUnique Parent Identifier: \"${getUniqueParentIdentifier()}\"" +
                "\nChild leaves in this folder: $childLeaves"
    }
    
    fun getUniqueIdentifier (): String {
        val fullPathClone = fullPath.toMutableList()
        
        return fullPathClone.joinToString("-")
    }
    
    fun getUniqueParentIdentifier (): String {
        val parentLeafFullPathClone = parentLeafFullPath.toMutableList()
        
        if (type == "folder" && name != "/") {
            parentLeafFullPathClone.removeAt(parentLeafFullPathClone.lastIndex)
        }
        
        return parentLeafFullPathClone.joinToString("-")
    }
    
    fun updateLeafSize(amountToBeAdded: Int) {
        leafSize += amountToBeAdded
    }
}

fun main () {
    fun isNumber (testedString: String): Boolean {
        return try {
            testedString.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }
    
    fun getLeaf(uniqueLeafIdentifier: String, completeLeafList: MutableList<Leaf>): Leaf {
        completeLeafList.map {leaf ->
            if (leaf.getUniqueIdentifier() == uniqueLeafIdentifier){
                return leaf
            }
        }
        
        return Leaf("NOTHING")
    }
    
    fun getChildLeaves (uniqueParentLeafIdentifier: String, completeLeafList: MutableList<Leaf>): MutableList<Leaf> {
        val listOfChildren: MutableList<Leaf> = mutableListOf()
        
        completeLeafList.map {leaf ->
            if (leaf.getUniqueParentIdentifier() == uniqueParentLeafIdentifier) {
                listOfChildren.add(leaf)
            }
        }
        
        return listOfChildren
    }
    
    fun calculateFileSizes (completeLeafList: MutableList<Leaf>, type: String) {
        completeLeafList.map {leaf ->
            if (leaf.type == type) {
                val parentLeaf = getLeaf(leaf.getUniqueParentIdentifier(), completeLeafList)
                println("parentLeaf: ${parentLeaf.name} being updated by: ${leaf.name}")
                parentLeaf.updateLeafSize(leaf.leafSize)
            }
        }
    }

    fun createLeafList (input: List<String>, breadCrumbs: BreadCrumbs): MutableList<Leaf> {
        val completeLeafList = mutableListOf<Leaf>()
        
        input.map {line ->
            val splitLine = line.split(" ")
            val lineActionOrSize = splitLine[0]
            val lineLeafName = splitLine[1]

            when {
                lineActionOrSize == "$" -> {
                    if (splitLine[1] == "cd") {
                        when (val lineDirectory = splitLine[2]) {
                            ".." -> {
                                if (breadCrumbs.fullPath.size > 0) {
                                    breadCrumbs.fullPath.removeAt(breadCrumbs.fullPath.lastIndex)
                                } else {
                
                                }
                            }
                            "/" -> {
                                breadCrumbs.fullPath.clear()
                                breadCrumbs.fullPath.add("/")
                            }
                            else -> {
                                breadCrumbs.deepestPath.add(lineDirectory)
                                breadCrumbs.fullPath.add(lineDirectory)
            
                                val leaf = Leaf(
                                    lineDirectory,
                                    0,
                                    "folder",
                                    breadCrumbs.getClonedFullPath(),
                                    breadCrumbs.getParentLeafFullPath()
                                )
    
                                // if(leaf.getUniqueParentIdentifier() != "") {
                                //     val parentLeaf = getLeaf(leaf.getUniqueParentIdentifier(), completeLeafList)
                                //     parentLeaf.childLeaves.add(leaf)
                                // }
                                
                                completeLeafList.add(leaf)
                            }
                        }
                    } else {
                    
                    }
                }
                isNumber(lineActionOrSize) -> {
                    val leafSize = lineActionOrSize.toInt()
                    
                    val leaf = Leaf(
                        lineLeafName,
                        leafSize,
                        "file",
                        breadCrumbs.getClonedFullPath(lineLeafName),
                        breadCrumbs.getParentLeafFullPath()
                    )
                    completeLeafList.add(leaf)
                }
                else -> {
                }
            }
        }
    
        completeLeafList.add(Leaf("/", 0, "folder", mutableListOf("/")))
    
        calculateFileSizes(completeLeafList, "file")
        calculateFileSizes(completeLeafList, "folder")
        
        return completeLeafList
    }
    
    fun getTotalSizeOfAllSub100K(completeLeafList: MutableList<Leaf>): Int {
        var total: Int = 0
        
        completeLeafList.map {leaf ->
            if (leaf.leafSize <= 100_000) {
                total += leaf.leafSize
            }
        }
        
        return total
    }

    fun part1 (input: List<String>) {
        val breadCrumbs = BreadCrumbs()
        val completeLeafList = createLeafList(input, breadCrumbs)
        
        // TODO: Fix Order of updates or fix the way they are being traversed... have no idea... tired .. must sleep
        
        println("Complete leaf list: $completeLeafList")
        
        println("Total of sub 100k folders: ${getTotalSizeOfAllSub100K(completeLeafList)}")
        
        println("breadCrumbs.deepestPath: ${breadCrumbs.deepestPath}")
    }

    fun part2 (input: List<String>) {

    }

    part1(part1controlInput)
    //part1(input)
}