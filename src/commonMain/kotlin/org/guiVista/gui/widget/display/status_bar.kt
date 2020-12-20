package org.guiVista.gui.widget.display

import org.guiVista.gui.layout.Container

/** Report messages of minor importance to the user. */
public expect class StatusBar : Container {
    /**
     * Gets a new context identifier given a description of the actual context. Note that the description is not shown
     * in the UI.
     * @param contextDescription Textual description of what context the new message is being used in.
     * @return A ID.
     */
    public infix fun fetchContextId(contextDescription: String): UInt

    /**
     * Pushes a new message onto a statusbar’s stack.
     * @param contextId The message’s context ID as returned by [fetchContextId].
     * @param text The message to add to the [StatusBar].
     * @return A message ID that can be used with [remove].
     */
    public fun push(contextId: UInt, text: String): UInt

    /**
     * Removes the first message in the [StatusBar's][StatusBar] stack with the given context ID. Note that this may
     * not change the displayed message, if the message at the top of the stack has a different context ID.
     * @param contextId A context identifier.
     */
    public fun pop(contextId: UInt)

    /**
     * Forces the removal of a message from a [StatusBar's][StatusBar] stack. The exact [contextId] and [messageId]
     * must be specified.
     * @param contextId A context identifier.
     * @param messageId The message identifier as returned by [push].
     */
    public fun remove(contextId: UInt, messageId: UInt)

    /**
     * Forces the removal of all messages from a [StatusBar's][StatusBar] stack with the exact [contextId] .
     * @param contextId The context identifier.
     */
    public fun removeAll(contextId: UInt)
}
