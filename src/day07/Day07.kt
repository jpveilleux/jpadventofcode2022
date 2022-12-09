package day07

import readInput
import java.lang.NumberFormatException

// ##########################################################################
//   Start of Setup  ########################################################

const val currentDay = 7
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
        
        if (type == "folder" && name != "/" && name != "NOTHING") {
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

    fun createLeafList (input: List<String>, breadCrumbs: BreadCrumbs): MutableList<Leaf> {
        val completeLeafList = mutableListOf<Leaf>()
        val allBreadCrumbs = mutableListOf<MutableList<String>>()
        
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
                                
                                allBreadCrumbs.add(leaf.fullPath)
                                
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
                    allBreadCrumbs.add(leaf.fullPath)
                    completeLeafList.add(leaf)
                }
                else -> {
                }
            }
        }
        
        val rootLeaf = Leaf("/", 0, "folder", mutableListOf("/"))
        completeLeafList.add(rootLeaf)
        
        allBreadCrumbs.add(rootLeaf.fullPath)
        allBreadCrumbs.sortWith(compareByDescending { it.size })
        allBreadCrumbs.map { strings ->
            val leafUniqueIdentifier = strings.joinToString("-")
            val leaf = getLeaf(leafUniqueIdentifier, completeLeafList)
            val parentLeaf = getLeaf(leaf.getUniqueParentIdentifier(), completeLeafList)
            
            parentLeaf.updateLeafSize(leaf.leafSize)
        }
    
        return completeLeafList
    }
    
    fun getTotalSizeOfAllSub100K(completeLeafList: MutableList<Leaf>): Int {
        var total: Int = 0
        
        completeLeafList.map {leaf ->
            if (leaf.type == "folder" && leaf.leafSize <= 100_000) {
                total += leaf.leafSize
            }
        }
        
        return total
    }
    
    fun getDirectoriesBySize (completeLeafList: MutableList<Leaf>, reversed: Boolean = false): MutableList<Leaf> {
        val dirBySize = mutableListOf<Leaf>()
        
        completeLeafList.map {leaf ->
            if (leaf.type == "folder") {
                dirBySize.add(leaf)
            }
        }
        
        dirBySize.sortWith(compareByDescending { it.leafSize })
        
        if (reversed) {
            return dirBySize.reversed().toMutableList()
        }
        
        return dirBySize
    }
    

    fun part1 (input: List<String>): Int {
        val breadCrumbs = BreadCrumbs()
        val completeLeafList = createLeafList(input, breadCrumbs)
        
        return getTotalSizeOfAllSub100K(completeLeafList)
    }

    fun part2 (input: List<String>): String {
        val breadCrumbs = BreadCrumbs()
        val completeLeafList = createLeafList(input, breadCrumbs)
        val totalDriveCapacity = 70_000_000
        val availableSpaceBeginning = totalDriveCapacity - getLeaf("/", completeLeafList).leafSize
        val minimumAvailableSpaceNeeded = 30_000_000
        
        println("/ size = ${getLeaf("/", completeLeafList).leafSize}")
        
        println("Total drive capacity: $totalDriveCapacity")
        println("Drive space available: $availableSpaceBeginning")
    
        println("Total of sub 100k folders: ${getTotalSizeOfAllSub100K(completeLeafList)}")
        
        val foldersBySizeAsc = getDirectoriesBySize(completeLeafList, true)
        var currentCumulativeSize = 0
        
        foldersBySizeAsc.map {folder ->
            currentCumulativeSize += folder.leafSize
            //val availableSizeIfCurrentFolderRemoved = availableSpaceBeginning + currentCumulativeSize
            val availableSizeIfCurrentFolderRemoved = availableSpaceBeginning + folder.leafSize
    
            println("----------------------------------------------------------------------")
            println("Current folder: ${folder.name}")
            println("Current folder full path: ${folder.fullPath}")
            println("size: \"${folder.leafSize}\"")
            println("SS: \"$availableSpaceBeginning\" - AFD: \"$availableSizeIfCurrentFolderRemoved\"")
            
            
            if (availableSizeIfCurrentFolderRemoved >= minimumAvailableSpaceNeeded) {
                return folder.name
            }
        }
        
        return "Piff Paff"
    }
    
    //  ##########################################################################
    //    Validation / Tests  ####################################################
    
    // check(part1(part1controlInput) == 95437)
    // check(part1(input) == 1243729)
    // check(part2(part1controlInput) == "d")
    
    //  ##########################################################################

    
    //part1(part1controlInput)
    //part1(input)
    //println("Part2 control: ${part2(part1controlInput)}")
    println("----------------------------------------------------------------------")
    println("Part2 real deal: ${part2(input)}")
}