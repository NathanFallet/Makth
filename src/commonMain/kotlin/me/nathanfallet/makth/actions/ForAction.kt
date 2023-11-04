package me.nathanfallet.makth.actions

import me.nathanfallet.makth.exceptions.ExecutionException
import me.nathanfallet.makth.exceptions.NotIterableException
import me.nathanfallet.makth.exceptions.UnknownVariablesException
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.extensions.indentedLines
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Iterable
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentTypeException
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.variables.Variable
import kotlin.js.JsExport
import kotlin.jvm.JvmStatic

/**
 * Action that executes a list of actions for a given set or interval.
 * @param identifier Identifier of the current iterated value
 * @param iterable Iterable to iterate during the loop
 * @param actions Actions to execute while condition is true
 */
@JsExport
data class ForAction(val identifier: String, val iterable: Value, val actions: List<Action>) : Action {

    companion object {

        /**
         * Handler for for action
         * @param args List of arguments
         * @return Action created from arguments
         */
        @JvmStatic
        fun handler(args: List<Value>): Action {
            if (args.count() != 2) {
                throw IncorrectArgumentCountException("for", args.count(), 2)
            }
            val identifier = when (val identifierValue = args[0]) {
                is Variable -> identifierValue.name
                is StringValue -> identifierValue.value
                else -> throw IncorrectArgumentTypeException("for", identifierValue, Variable::class)
            }
            return ForAction(identifier, args[1], listOf())
        }
    }

    @Throws(ExecutionException::class)
    override fun execute(context: Context): Context {
        // Eval iterator
        val evaluatedIterable = iterable.compute(context)

        // Check if there are missing variables
        evaluatedIterable.variables.takeIf { it.isNotEmpty() }?.let {
            throw UnknownVariablesException(this, context, it)
        }

        // Check if condition is a boolean
        if (evaluatedIterable !is Iterable) {
            throw NotIterableException(this, context, evaluatedIterable)
        }

        // Iterate
        val iterator = evaluatedIterable.iterator
        var newContext = context
        while (iterator.hasNext()) {
            // Get next value
            val value = iterator.next()

            // Create new context
            newContext = Context(newContext.data + mapOf(identifier to value), newContext.outputs)

            // Execute actions
            for (action in actions) {
                newContext = newContext.execute(action)
            }
        }
        return newContext
    }

    override val algorithmString: String get() {
        val builder = StringBuilder()
        builder.append("for (")
        builder.append(identifier)
        builder.append(", ")
        builder.append(iterable.algorithmString)
        builder.append(") {")
        for (action in actions) {
            builder.append("\n")
            builder.append(action.algorithmString.indentedLines)
        }
        builder.append("\n}")
        return builder.toString()
    }
}
