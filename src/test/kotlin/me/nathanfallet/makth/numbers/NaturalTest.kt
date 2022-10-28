package me.nathanfallet.makth.numbers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.math.PI

class NaturalTest {

    @Test
    fun toRawString() {
        assertEquals("5", Integer.instantiate(5).toRawString())
    }

    @Test
    fun toLaTeXString() {
        assertEquals("5", Integer.instantiate(5).toLaTeXString())
    }

    @Test
    fun negativeThrows() {
        assertThrows(IllegalArgumentException::class.java) {
            Natural.instantiate(-1)
        }
    }

    @Test
    fun sumCorrectNatural() {
        // 2 + 3 = 5
        assertEquals(Integer.instantiate(5), Integer.instantiate(2).sum(Integer.instantiate(3)))
    }

    @Test
    fun sumCorrectInteger() {
        // 2 + -3 = -1
        assertEquals(Integer.instantiate(-1), Integer.instantiate(2).sum(Integer.instantiate(-3)))
    }

    @Test
    fun sumCorrectRational() {
        // 1 + 1/2 = 3/2
        assertEquals(
            Rational.instantiate(3, 2),
            Integer.instantiate(1).sum(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun sumCorrectReal() {
        // 2 + pi = 2 + pi
        assertEquals(
            Real.instantiate(2 + PI),
            Integer.instantiate(2).sum(Real.pi)
        )
    }

    @Test
    fun multiplyCorrectNatural() {
        // 2 * 3 = 6
        assertEquals(
            Integer.instantiate(6),
            Integer.instantiate(2).multiply(Integer.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // 2 * -3 = -6
        assertEquals(
            Integer.instantiate(-6),
            Integer.instantiate(2).multiply(Integer.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // 3 * 1/2 = 3/2
        assertEquals(
            Rational.instantiate(3, 2),
            Integer.instantiate(3).multiply(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // 2 * pi = 2 * pi
        assertEquals(
            Real.instantiate(2 * PI),
            Integer.instantiate(2).multiply(Real.pi)
        )
    }

    @Test
    fun divideCorrectNatural() {
        // 2 / 3 = 2/3
        assertEquals(
            Rational.instantiate(2, 3),
            Integer.instantiate(2).divide(Integer.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // 2 / -3 = -2/3
        assertEquals(
            Rational.instantiate(-2, 3),
            Integer.instantiate(2).divide(Integer.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // 3 / (5/2) = 6/5
        assertEquals(
            Rational.instantiate(6, 5),
            Integer.instantiate(3).divide(Rational.instantiate(5, 2))
        )
    }

    @Test
    fun divideCorrectReal() {
        // 2 / pi = 2 / pi
        assertEquals(
            Real.instantiate(2 / PI),
            Integer.instantiate(2).divide(Real.pi)
        )
    }

}