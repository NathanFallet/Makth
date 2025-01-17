package dev.makth.exceptions

import dev.makth.interfaces.Action
import dev.makth.interfaces.Value
import dev.makth.resolvables.Context
import kotlin.js.JsExport

/**
 * Exception thrown when a variable is not iterable
 * @param action Action that failed
 * @param context Context of the action
 * @param value Value that is not iterable
 */
@JsExport
open class NotIterableException(
    action: Action,
    context: Context,
    val value: Value,
) : ExecutionException(
    action, context,
    "Value is not iterable: ${value.rawString}"
)
