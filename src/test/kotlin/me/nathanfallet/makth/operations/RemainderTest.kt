package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class RemainderTest {

    private val context = Context()

    @Test
    fun toRawString() {
        assertEquals(
            "1 % 2",
            Remainder(Integer.instantiate(1), Integer.instantiate(2)).toRawString()
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) % (3 + 4)",
            Remainder(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "1 % 2",
            Remainder(Integer.instantiate(1), Integer.instantiate(2)).toLaTeXString()
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "(1 + 2) % (3 + 4)",
            Remainder(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).toLaTeXString()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Remainder(Variable.instantiate("x"), Variable.instantiate("y")).extractVariables()
        )
    }

    @Test
    fun divideNaturals() {
        // Check that a remainder is computed correctly
        assertEquals(
            Integer.instantiate(1),
            Remainder(Integer.instantiate(3), Integer.instantiate(2)).compute(context)
        )
    }

}