package oo.test

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import kotlin.test.assertEquals


/**
 * @author hanlipeng
 * @date 2023/2/7
 */
class CalculatorTest {

    @ParameterizedTest(name = "testData")
    fun testCalculator(points: List<Int>, result: Int) {
        val calculator = Calculator()
        points.forEach(calculator::nextPoint)
        assertEquals(result, calculator.totalPoints())
    }

    companion object {
        @JvmStatic
        fun testData(): List<Arguments> {
            return listOf(
                Arguments.of(listOf(1, 2), 3)
            )
        }
    }
}