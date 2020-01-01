package org.guivista.core.widget.tool

import gtk3.*
import kotlinx.cinterop.CPointer
import org.guivista.core.ObjectBase
import org.guivista.core.SizeGroup

/** An interface for containers containing tool item widgets. */
interface ToolShell : ObjectBase {
    val gtkToolShellPtr: CPointer<GtkToolShell>?
    /**
     * Retrieves the icon size for the [ToolShell]. Tool items must not call this function directly, but rely on
     * [org.guivista.core.widget.tool.item.ToolItem.iconSize] instead.
     */
    val iconSize: GtkIconSize
        get() = gtk_tool_shell_get_icon_size(gtkToolShellPtr)
    /**
     * Retrieves the current orientation for the [ToolShell]. Tool items must not call this function directly, but rely
     * on [org.guivista.core.widget.tool.item.ToolItem.orientation] instead.
     */
    val orientation: GtkOrientation
        get() = gtk_tool_shell_get_orientation(gtkToolShellPtr)
    /**
     * Returns the relief style of buttons on [ToolShell]. Tool items must not call this function directly, but rely on
     * [org.guivista.core.widget.tool.item.ToolItem.reliefStyle] instead.
     */
    val reliefStyle: GtkReliefStyle
        get() = gtk_tool_shell_get_relief_style(gtkToolShellPtr)
    /**
     * Retrieves whether the [ToolShell] has text, icons, or both. Tool items must not call this function directly, but
     * rely on [org.guivista.core.widget.tool.item.ToolItem.toolBarStyle] instead.
     */
    val style: GtkToolbarStyle
        get() = gtk_tool_shell_get_style(gtkToolShellPtr)
    /**
     * Retrieves the current text alignment for the [ToolShell]. Tool items must not call this function directly, but
     * rely on [org.guivista.core.widget.tool.item.ToolItem.textAlignment] instead.
     */
    val textAlignment: Float
        get() = gtk_tool_shell_get_text_alignment(gtkToolShellPtr)
    /**
     * Retrieves the current text orientation for the [ToolShell]. Tool items must not call this function directly, but
     * rely on [org.guivista.core.widget.tool.item.ToolItem.textOrientation] instead.
     */
    val textOrientation: GtkOrientation
        get() = gtk_tool_shell_get_text_orientation(gtkToolShellPtr)
    /**
     * Retrieves the current text size group for the [ToolShell]. Tool items must not call this function directly, but
     * rely on [org.guivista.core.widget.tool.item.ToolItem.textSizeGroup] instead.
     */
    val textSizeGroup: SizeGroup
        get() = SizeGroup(gtk_tool_shell_get_text_size_group(gtkToolShellPtr))

    /**
     * Calling this function signals the [ToolShell] that the overflow menu item for tool items have changed. If there
     * is an overflow menu, and if it is visible when this function is called then the menu **will** be rebuilt. Tool
     * items must not call this function directly, but rely on
     * [org.guivista.core.widget.tool.item.ToolItem.rebuildMenu] instead.
     */
    fun rebuildMenu() {
        gtk_tool_shell_rebuild_menu(gtkToolShellPtr)
    }
}
