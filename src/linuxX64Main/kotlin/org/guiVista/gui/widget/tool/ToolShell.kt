package org.guiVista.gui.widget.tool

import gtk3.*
import kotlinx.cinterop.CPointer
import org.guiVista.core.ObjectBase
import org.guiVista.gui.SizeGroup

public actual interface ToolShell : ObjectBase {
    public val gtkToolShellPtr: CPointer<GtkToolShell>?

    /**
     * Retrieves the icon size for the [ToolShell]. Tool items must not call this function directly, but rely on
     * [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [iconSize][org.guiVista.gui.widget.tool.item.ToolItem.iconSize] property instead.
     */
    public val iconSize: GtkIconSize
        get() = gtk_tool_shell_get_icon_size(gtkToolShellPtr)

    /**
     * Retrieves the current orientation for the [ToolShell]. Tool items must not call this function directly, but rely
     * on [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [orientation][org.guiVista.gui.widget.tool.item.ToolItem.orientation] property instead.
     */
    public val orientation: GtkOrientation
        get() = gtk_tool_shell_get_orientation(gtkToolShellPtr)

    /**
     * Returns the relief style of buttons on [ToolShell]. Tool items must not call this function directly, but rely on
     * [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [reliefStyle][org.guiVista.gui.widget.tool.item.ToolItem.reliefStyle] property instead.
     */
    public val reliefStyle: GtkReliefStyle
        get() = gtk_tool_shell_get_relief_style(gtkToolShellPtr)

    /**
     * Retrieves whether the [ToolShell] has text, icons, or both. Tool items must not call this function directly, but
     * rely on [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [toolbarStyle][org.guiVista.gui.widget.tool.item.ToolItem.toolBarStyle] property instead.
     */
    public val style: GtkToolbarStyle
        get() = gtk_tool_shell_get_style(gtkToolShellPtr)

    /**
     * Retrieves the current text alignment for the [ToolShell]. Tool items must not call this function directly, but
     * rely on [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [textAlignment][org.guiVista.gui.widget.tool.item.ToolItem.textAlignment] property instead.
     */
    public val textAlignment: Float
        get() = gtk_tool_shell_get_text_alignment(gtkToolShellPtr)

    /**
     * Retrieves the current text orientation for the [ToolShell]. Tool items must not call this function directly, but
     * rely on [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [textOrientation][org.guiVista.gui.widget.tool.item.ToolItem.textOrientation] property instead.
     */
    public val textOrientation: GtkOrientation
        get() = gtk_tool_shell_get_text_orientation(gtkToolShellPtr)

    /**
     * Retrieves the current text size group for the [ToolShell]. Tool items must not call this function directly, but
     * rely on [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [textSizeGroup][org.guiVista.gui.widget.tool.item.ToolItem.textSizeGroup] property instead.
     */
    public val textSizeGroup: SizeGroup
        get() = SizeGroup(gtk_tool_shell_get_text_size_group(gtkToolShellPtr))

    /**
     * Calling this function signals the [ToolShell] that the overflow menu item for tool items have changed. If there
     * is an overflow menu, and if it is visible when this function is called then the menu **will** be rebuilt. Tool
     * items must not call this function directly, but rely on
     * [ToolItem's][org.guiVista.gui.widget.tool.item.ToolItem]
     * [rebuildMenu][org.guiVista.gui.widget.tool.item.ToolItem.rebuildMenu] function instead.
     */
    public fun rebuildMenu() {
        gtk_tool_shell_rebuild_menu(gtkToolShellPtr)
    }
}
