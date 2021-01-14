package org.guiVista.gui.window

/**
 * Represents a top level window that can contain other widgets. Nearly always, the type of the window should be
 * `GTK_WINDOW_TOPLEVEL`. If you’re implementing something like a popup menu from scratch (which is a bad idea, just use
 * GtkMenu), you might use `GTK_WINDOW_POPUP`. Note that `GTK_WINDOW_POPUP` is not for dialogs, though in some other
 * toolkit dialogs are called “popups”. In GTK `GTK_WINDOW_POPUP` means a pop-up menu, or pop-up tooltip. On X11 popup
 * windows aren't controlled by the window manager.
 *
 * If you simply want an undecorated window (no window borders), use `decorated` property, don’t use
 * `GTK_WINDOW_POPUP`. All top level windows are stored in an internal top level window list. This list can be obtained
 * from [listTopLevelWindows]. To remove a window call `close()`.
 */
public expect abstract class Window : WindowBase
