package me.nathanfallet.makth.extensions

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * Boolean value
 * @param value Boolean value
 */
data class BooleanValue(val value: Boolean) : Value {

    override fun compute(context: Context): Value {
        return this
    }

    override fun toRawString(): String {
        return if (value) "true" else "false"
    }

    override fun toLaTeXString(): String {
        return if (value) "\\text{true}" else "\\text{false}"
    }

    override fun extractVariables(): Set<Variable> {
        return setOf()
    }

}