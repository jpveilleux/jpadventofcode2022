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

    val shouldWhat = mapOf(
        "LOSE" to "X",
        "DRAW" to "Y",
        "WIN" to "Z"
    )


    val testInputFileName = "Day02_test";
    val inputFileName = "Day02";
    val testInput = readInput(testInputFileName)
    val input = readInput(inputFileName)

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val oppMove = it.substring(0..0);
            val shouldDoMove = it.substring(2..2);

            when (oppMove) {
                oppMoves["rock"] -> {
                    when (shouldDoMove) {
                        shouldWhat["DRAW"] -> {
                            outcomes["DRAW"]!! + myMoveScores[myMoves["rock"]]!!;
                        }
                        shouldWhat["WIN"] -> {
                            outcomes["WIN"]!! + myMoveScores[myMoves["paper"]]!!;
                        }
                        shouldWhat["LOSE"] -> {
                            outcomes["LOSE"]!! + myMoveScores[myMoves["scissors"]]!!;
                        }
                        else -> 0
                    }
                }
                oppMoves["paper"] -> {
                    when (shouldDoMove) {
                        shouldWhat["DRAW"] -> {
                            outcomes["DRAW"]!! + myMoveScores[myMoves["paper"]]!!;
                        }
                        shouldWhat["WIN"] -> {
                            outcomes["WIN"]!! + myMoveScores[myMoves["scissors"]]!!;
                        }
                        shouldWhat["LOSE"] -> {
                            outcomes["LOSE"]!! + myMoveScores[myMoves["rock"]]!!;
                        }
                        else -> 0
                    }
                }
                oppMoves["scissors"] -> {
                    when (shouldDoMove) {
                        shouldWhat["DRAW"] -> {
                            outcomes["DRAW"]!! + myMoveScores[myMoves["scissors"]]!!;
                        }
                        shouldWhat["WIN"] -> {
                            outcomes["WIN"]!! + myMoveScores[myMoves["rock"]]!!;
                        }
                        shouldWhat["LOSE"] -> {
                            outcomes["LOSE"]!! + myMoveScores[myMoves["paper"]]!!;
                        }
                        else -> 0
                    }
                }
                else -> 0
            }
        }
    }

    println(part1(input));

    fun part2(input: List<String>) {
    }
}
