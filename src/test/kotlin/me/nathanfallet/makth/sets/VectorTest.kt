package me.nathanfallet.makth.sets

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.extensions.StringValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class VectorTest {

    @Test
    fun rawString() {
        assertEquals(
            "(1; 2; 3)",
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "\\begin{pmatrix} 1 \\\\ 2 \\\\ 3 \\end{pmatrix}",
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Vector.instantiate(listOf(Variable.instantiate("x"), Variable.instantiate("y"))).variables
        )
    }

    @Test
    fun elements() {
        assertEquals(
            listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3)),
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).elements
        )
    }

    @Test
    fun rows() {
        assertEquals(
            listOf(listOf(Integer.instantiate(1)), listOf(Integer.instantiate(2)), listOf(Integer.instantiate(3))),
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).rows
        )
    }

    @Test
    fun columns() {
        assertEquals(
            listOf(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))),
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).columns
        )
    }

    @Test
    fun sumCorrectVector() {
        assertEquals(
            Vector.instantiate(listOf(Integer.instantiate(5), Integer.instantiate(7), Integer.instantiate(9))),
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).sum(
                Vector.instantiate(listOf(Integer.instantiate(4), Integer.instantiate(5), Integer.instantiate(6)))
            )
        )
    }

    @Test
    fun sumIncorrectVector() {
        assertThrows(UnsupportedOperationException::class.java) {
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).sum(
                Vector.instantiate(listOf(Integer.instantiate(4), Integer.instantiate(5)))
            )
        }
    }

    @Test
    fun sumUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).sum(
                StringValue("test")
            )
        }
    }

    @Test
    fun multiplyCorrectReal() {
        assertEquals(
            Vector.instantiate(listOf(Integer.instantiate(2), Integer.instantiate(4), Integer.instantiate(6))),
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).multiply(
                Integer.instantiate(2)
            )
        )
    }

    @Test
    fun multiplyUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).multiply(
                StringValue("test")
            )
        }
    }

}