package oo.test

/**
 * @author hanlipeng
 * @date 2023/2/18
 */
class GuessNumberCalculator {
}

fun main() {
    var score = 0
    for (i in 1..1000) {
        val game = GuessNumber()
        var solution = init()
        var turnNumber = 0
        println("new turn, game number is ${game.gameResult()}")
        while (true) {
            val value = solution.getMaxProbability()
            val result = game.compareNumber(value)
            turnNumber++
            println("turn: $turnNumber, guess value ${value.joinToString()}, guess result $result")
            if (result == "4A0B") {
                break
            }
            solution = solution.next(result, value)
        }
        score += turnNumber
        println("one turn down, current score is $turnNumber, avg score is ${score.toDouble() / i}")
    }
    println(score.toDouble() / 1000)
}