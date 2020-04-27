package org.guiVista.gui.widget.button

/** Create buttons bound to a URL. */
expect class LinkButton : ButtonBase {
    /** The URI bound to this button. Default value is *""* (an empty String) */
    var uri: String

    /** The *visited* state of this button. A visited link is drawn in a different color. Default value is *false*. */
    var visited: Boolean

}
