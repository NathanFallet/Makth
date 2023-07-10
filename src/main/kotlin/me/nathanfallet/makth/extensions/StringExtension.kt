package me.nathanfallet.makth.extensions

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * String value
 * @param value String value
 */
data class StringValue(val value: String, val latex: Boolean = false) : Value {

    override fun compute(context: Context): Value {
        return this
    }

    override val algorithmString: String get() {
        val char = if (latex) "$" else "\""
        return "$char${value.replace("$char", "\\$char")}$char"
    }

    override val rawString: String get() {
        return value
    }

    override val laTeXString: String get() {
        return if (latex) value.replace("\$", "\\\$") else "\\text{${value.replace("{", "\\{").replace("}", "\\}")}}"
    }

    override val variables: Set<Variable> get() {
        return setOf()
    }

    override fun equals(right: Value): Boolean {
        if (right is StringValue) {
            return value == right.value
        }
        return super.equals(right)
    }

}

val String.indentedLines: String get() {
    return split("\n").joinToString("\n") { "    $it" }
}