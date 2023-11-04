package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.indentedLines
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.resolvables.Context

/**
 * Action that executes a list of actions if a condition is true,
 * or another list of actions otherwise.
 * @param condition Condition to check
 * @param actions Actions to execute if condition is true
 * @param elseActions Actions to execute if condition is false
 */
data class IfAction(
        val condition: Value,
        val actions: List<Action>,
        val elseActions: List<Action> = listOf()
) : Action {

    companion object {

        /**
         * Handler for if action
         * @param args List of arguments
         * @return Action created from arguments
         */
        fun handler(args: List<Value>): Action {
            if (args.count() != 1) {
                throw IncorrectArgumentCountException("if", args.count(), 1)
            }
            return IfAction(args[0], listOf())
        }
    }

    @Throws(Action.ExecutionException::class)
    override fun execute(context: Context): Context {
        // Eval condition
        val evaluatedCondition = condition.compute(context)

        // Check if there are missing variables
        evaluatedCondition.variables.takeIf { it.isNotEmpty() }?.let {
            throw Action.UnknownVariablesException(this, context, it)
        }

        // Check if condition is a boolean
        if (evaluatedCondition !is BooleanValue) {
            throw Action.NotABooleanException(this, context, evaluatedCondition)
        }

        // Execute if it is true
        return if (evaluatedCondition.value) {
            context.execute(actions)
        } else {
            context.execute(elseActions)
        }
    }

    override val algorithmString: String get() {
        val builder = StringBuilder()
        builder.append("if (")
        builder.append(condition.algorithmString)
        builder.append(") {")
        for (action in actions) {
            builder.append("\n")
            builder.append(action.algorithmString.indentedLines)
        }
        if (elseActions.isNotEmpty()) {
            builder.append("\n} else {")
            for (action in elseActions) {
                builder.append("\n")
                builder.append(action.algorithmString.indentedLines)
            }
        }
        builder.append("\n}")
        return builder.toString()
    }
}