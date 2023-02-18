package oo.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CalculatorTest {

    @Test
    fun testPerfectGame() {
        val calc = Calculator()
        repeat(12) { calc.nextPoint(10) }
        assertEquals(300, calc.totalPoints())
    }

    @Test
    fun testAllSpares() {
        val calc = Calculator()
        repeat(21) { calc.nextPoint(5) }
        assertEquals(150, calc.totalPoints())
    }

    @Test
    fun testAllNines() {
        val calc = Calculator()
        repeat(10) { calc.nextPoint(9); calc.nextPoint(0) }
        assertEquals(90, calc.totalPoints())
    }

    @Test
    fun testInvalidPoint() {
        val calc = Calculator()
        calc.nextPoint(10)
        assertThrows(InvalidPointException::class.java) { calc.nextPoint(11) }
    }

    @Test
    fun testGameHasFinishedException() {
        val calc = Calculator()
        repeat(12) { calc.nextPoint(10) }
        assertThrows(GameHasFinishedException::class.java) { calc.nextPoint(10) }
    }
}