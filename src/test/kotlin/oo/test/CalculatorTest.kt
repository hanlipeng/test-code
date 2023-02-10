package oo.test

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals


/**
 * @author hanlipeng
 * @date 2023/2/7
 */
class CalculatorTest {

    @ParameterizedTest()
    @MethodSource("testData")
    fun testCalculator(points: List<Int>, result: Int) {
        val calculator = Calculator()
        points.forEach(calculator::nextPoint)
        assertEquals(result, calculator.totalPoints())
    }

    companion object {
        @JvmStatic
        fun testData(): List<Arguments> {
            val fullStrike =
                listOf(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10)
            return listOf(
//                Arguments.of(listOf(1, 2), 3),
//                Arguments.of(listOf(1, 2, 1), 3),
//                Arguments.of(listOf(1, 2, 1), 3),
                Arguments.of(listOf(1, 2, 3,2,1,2,3,2,1,2,3,2,1,2,3,2,1,2,3,2), 40),
                Arguments.of(fullStrike, 300),
            )
        }
    }
}