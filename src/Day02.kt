fun main() {
    // Opponent
    // -----------------
    // A = Rock
    // B = Paper
    // C = Scissors

    // Player
    // -----------------
    // X = Rock = 1
    // Y = Paper = 2
    // Z = Scissors = 3

    val myMoveScores = mapOf(
        "X" to 1,
        "Y" to 2,
        "Z" to 3
    )

    // Outcome Scores
    // -----------------
    // Win = 6
    // Draw = 3
    // Lost = 0

    val outcomes = mapOf(
        "WIN" to 6,
        "DRAW" to 3,
        "LOSE" to 0
    )

    val oppMoves = mapOf(
        "rock" to "A",
        "paper" to "B",
        "scissors" to "C"
    )

    val myMoves = mapOf(
        "rock" to "X",
        "paper" to "Y",
        "scissors" to "Z"
    )


    val testInputFileName = "Day02_test";
    val inputFileName = "Day02";
    val testInput = readInput(testInputFileName)
    val input = readInput(inputFileName)

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val oppMove = it.substring(0..0);
            val myMove = it.substring(2..2);

            when (oppMove) {
                oppMoves["rock"] -> {
                    when (myMove) {
                        myMoves["rock"] -> {
                            outcomes["DRAW"]!! + myMoveScores[myMove]!!;
                        }
                        myMoves["paper"] -> {
                            outcomes["WIN"]!! + myMoveScores[myMove]!!;
                        }
                        myMoves["scissors"] -> {
                            outcomes["LOSE"]!! + myMoveScores[myMove]!!;
                        }
                        else -> 0
                    }
                }
                oppMoves["paper"] -> {
                    when (myMove) {
                        myMoves["rock"] -> {
                            outcomes["LOSE"]!! + myMoveScores[myMove]!!;
                        }
                        myMoves["paper"] -> {
                            outcomes["DRAW"]!! + myMoveScores[myMove]!!;
                        }
                        myMoves["scissors"] -> {
                            outcomes["WIN"]!! + myMoveScores[myMove]!!;
                        }
                        else -> 0
                    }
                }
                oppMoves["scissors"] -> {
                    when (myMove) {
                        myMoves["rock"] -> {
                            outcomes["WIN"]!! + myMoveScores[myMove]!!;
                        }
                        myMoves["paper"] -> {
                            outcomes["LOSE"]!! + myMoveScores[myMove]!!;
                        }
                        myMoves["scissors"] -> {
                            outcomes["DRAW"]!! + myMoveScores[myMove]!!;
                        }
                        else -> 0
                    }
                }
                else -> 0
            }
        }
    }

    println(part1(testInput));

    fun part2(input: List<String>) {
    }
}
