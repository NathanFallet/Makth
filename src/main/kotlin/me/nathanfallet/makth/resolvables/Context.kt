package me.nathanfallet.makth.resolvables

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value

data class Context(
    val data: Map<String, Value> = hashMapOf(),
    val logs: List<String> = listOf()
) {

    @Throws(Action.ExecutionException::class)
    fun execute(action: Action): Context {
        return action.execute(this)
    }

    @Throws(Action.ExecutionException::class)
    fun execute(actions: List<Action>): Context {
        var context = this
        for (action in actions) {
            context = action.execute(context)
        }
        return context
    }

}