package me.nathanfallet.makth.actions

import me.nathanfallet.makth.exceptions.NotIterableException
import me.nathanfallet.makth.exceptions.UnknownVariablesException
import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.numbers.integers.IntegerFactory
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.variables.VariableFactory
import me.nathanfallet.makth.sets.vectors.VectorFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ForActionTest {

    private val context = Context()

    private val contextWithOutput = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(3)
        ),
        listOf(
            IntegerFactory.instantiate(1), StringValue("\n"),
            IntegerFactory.instantiate(2), StringValue("\n"),
            IntegerFactory.instantiate(3), StringValue("\n")
        )
    )

    @Test
    fun algorithmString() {
        assertEquals(
            "for (x, (1; 2; 3)) {\n}",
            ForAction(
                "x",
                VectorFactory.instantiate(
                    listOf(
                        IntegerFactory.instantiate(1),
                        IntegerFactory.instantiate(2),
                        IntegerFactory.instantiate(3)
                    )
                ),
                listOf()
            ).algorithmString
        )
    }

    @Test
    fun toRawStringWithChild() {
        assertEquals(
            "for (x, (1; 2; 3)) {\n    print(x)\n}",
            ForAction(
                "x",
                VectorFactory.instantiate(
                    listOf(
                        IntegerFactory.instantiate(1),
                        IntegerFactory.instantiate(2),
                        IntegerFactory.instantiate(3)
                    )
                ),
                listOf(PrintAction(listOf(VariableFactory.instantiate("x"))))
            ).algorithmString
        )
    }

    @Test
    fun handler() {
        assertEquals(
            ForAction(
                "x",
                VectorFactory.instantiate(
                    listOf(
                        IntegerFactory.instantiate(1),
                        IntegerFactory.instantiate(2),
                        IntegerFactory.instantiate(3)
                    )
                ),
                listOf()
            ),
            ForAction.handler(listOf(
                VariableFactory.instantiate("x"),
                VectorFactory.instantiate(
                    listOf(
                        IntegerFactory.instantiate(1),
                        IntegerFactory.instantiate(2),
                        IntegerFactory.instantiate(3)
                    )
                )
            ))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertFailsWith(IncorrectArgumentCountException::class) {
            ForAction.handler(listOf())
        }
    }

    @Test
    fun forWithPrint() {
        assertEquals(
            contextWithOutput,
            context.execute(
                ForAction(
                    "x",
                    VectorFactory.instantiate(
                        listOf(
                            IntegerFactory.instantiate(1),
                            IntegerFactory.instantiate(2),
                            IntegerFactory.instantiate(3)
                        )
                    ),
                    listOf(PrintAction(listOf(VariableFactory.instantiate("x"))))
                )
            )
        )
    }

    @Test
    fun forWithPrintWithoutContext() {
        assertFailsWith(UnknownVariablesException::class) {
            context.execute(
                ForAction("x", VariableFactory.instantiate("y"), listOf())
            )
        }
    }

    @Test
    fun forWhenNotIterable() {
        assertFailsWith(NotIterableException::class) {
            context.execute(
                ForAction("x", BooleanValue(true), listOf())
            )
        }
    }

}
