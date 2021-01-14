package org.guiVista.gui.window

import org.guiVista.core.dataType.DoublyLinkedList
import org.guiVista.gui.layout.Container

/** Base interface for window objects. */
public expect interface WindowBase : Container

/**
 * Returns a list of all existing top level windows. The widgets in the list are not individually referenced. If you
 * want to iterate through the list and perform actions involving callbacks that might destroy the widgets, you must
 * call `g_list_foreach (result, (GFunc)g_object_ref, NULL)` first, and then unreference **ALL** the widgets afterwards.
 * @return A list of the top level windows.
 */
public expect fun listTopLevelWindows(): DoublyLinkedList
