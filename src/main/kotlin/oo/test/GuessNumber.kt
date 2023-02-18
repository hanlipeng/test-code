package oo.test

import java.util.*

/**
 * @author hanlipeng
 * @date 2023/2/17
 */
class GuessNumber {

    val number: List<Int>
    val scanner = Scanner(System.`in`)

    init {
        val numberPool = (0..9).toList()
        number = numberPool.shuffled().subList(0, 4)
    }

    fun play() {
        for (i in 0..5) {
            val next = scanner.next()
            val number = next.toCharArray().map { it.digitToInt() }.toList()
            val result = compareNumber(number)
            if (result == "4A0B") break
            println(result)
        }
        println("game done, result is: $number")
    }

    fun compareNumber(inputNumber: List<Int>): String {
        var correct = 0
        var positionCorrect = 0
        for (i in 0..3) {
            if (number[i] == inputNumber[i]) {
                correct++
            } else if (inputNumber[i] in number) {
                positionCorrect++
            }
        }

        return "${correct}A${positionCorrect}B"
    }

    fun gameResult(): String {
        return number.joinToString()
    }

}

fun main() {
    val guessNumber = GuessNumber()
    guessNumber.play()
}