package org.guiVista.core.dataType

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.reinterpret

/**
 * A linked list that can be iterated over in both directions. Remember to call [close] when you are finished with a
 * DoublyLinkedList instance. Maps to [GList](https://developer.gnome.org/glib/stable/glib-Doubly-Linked-Lists.html)
 * GLib data type.
 */
class DoublyLinkedList(listPtr: CPointer<GList>? = null) : _root_ide_package_.org.guiVista.core.Closable {
    private var _gListPtr = listPtr ?: g_list_alloc()
    val gListPtr: CPointer<GList>?
        get() = _gListPtr
    /** The number of elements in a list. */
    val length: UInt
        get() = g_list_length(_gListPtr)
    var data: CPointer<*>?
        get() = _gListPtr?.pointed?.data
        set(value) {
            _gListPtr?.pointed?.data = value
        }
    val next: DoublyLinkedList?
        get() {
            val tmp = _gListPtr?.pointed
            return if (tmp != null) DoublyLinkedList(tmp.next?.reinterpret()) else null
        }
    val prev: DoublyLinkedList?
        get() {
            val tmp = _gListPtr?.pointed
            return if (tmp != null) DoublyLinkedList(tmp.prev?.reinterpret()) else null
        }

    /**
     * Frees up the [DoublyLinkedList] instance. Note that only the list is freed. This function **MUST** be called to
     * prevent memory leaks when you are finished with the [DoublyLinkedList] instance.
     */
    override fun close() {
        g_list_free(_gListPtr)
        _gListPtr = null
    }

    /**
     * Adds a new element on to the end of the list.
     * @param data The data for the new element.
     * @see append
     */
    operator fun plusAssign(data: CPointer<*>?) {
        append(data)
    }

    /**
     * Adds a new element on to the end of the list. The return value is the new start of the list, which may have
     * changed so make sure you store the new value. Note that the entire list needs to be traversed to find the end,
     * which is inefficient when adding multiple elements. A common idiom to avoid the inefficiency is to prepend the
     * elements, and reverse the list when all elements have been added.
     * @param data The data for the new element.
     * @return The new start of the list.
     */
    fun append(data: CPointer<*>?): CPointer<GList>? = g_list_append(_gListPtr, data)

    /**
     * Removes an element from a list.
     * @param data The data of the element to remove.
     * @see remove
     */
    operator fun minusAssign(data: CPointer<*>?) {
        remove(data)
    }

    /**
     * Removes an element from a list. If two elements contain the same data, only the first is removed. If none of the
     * elements contain the data the list is unchanged.
     * @param data The data of the element to remove.
     * @return The new start of the list.
     */
    fun remove(data: CPointer<*>?) = g_list_remove(_gListPtr, data)

    /**
     * Adds a new element on to the start of the list. The return value is the new start of the list, which may have
     * changed so make sure you store the new value.
     * @param data The data for the new element.
     * @return The data for the new element.
     */
    fun prepend(data: CPointer<*>?): CPointer<GList>? = g_list_prepend(_gListPtr, data)

    /**
     * Removes all list nodes with data equal to data. Returns the new head of the list. Contrast with [remove], which
     * removes only the first node matching the given data.
     * @param data The data to remove.
     * @return The new head of the list.
     */
    fun removeAll(data: CPointer<*>?): CPointer<GList>? = g_list_remove_all(_gListPtr, data)

    /**
     * Inserts a new element into the list at the given position.
     * @param data The data for the new element.
     * @param position The position to insert the element. If this is negative, or is larger than the number of
     * elements in the list, then the new element is added on to the end of the list.
     * @return The new start of the list.
     */
    fun insert(data: CPointer<*>?, position: Int): CPointer<GList>? =
        g_list_insert(list = _gListPtr, data = data, position = position)

    /**
     * Inserts a node before sibling containing data .
     * @param sibling A node to insert data before.
     * @param data The data to put in the newly inserted node.
     * @return The new head of the list.
     */
    fun insertBefore(sibling: DoublyLinkedList, data: CPointer<*>?): CPointer<GList>? =
        g_list_insert_before(list = _gListPtr, sibling = sibling.gListPtr, data = data)

    /**
     * Reverses a list.
     * @return The start of the reversed list.
     */
    fun reverse(): CPointer<GList>? = g_list_reverse(_gListPtr)

    /**
     * Finds the element in a GSList which contains the given data.
     * @param data The element data to find.
     * @see find
     */
    operator fun contains(data: CPointer<*>?): Boolean = find(data) != null

    /**
     * Finds the element in a GSList which contains the given data.
     * @param data The element data to find.
     * @return The found list element, or *null* if it isn't found.
     */
    fun find(data: CPointer<*>?): CPointer<GList>? = g_list_find(_gListPtr, data)

    /**
     * Gets the position of the given element in the list (starting from 0).
     * @param listLink An element in the list.
     * @return The position of the element in the list, or *-1* if the element isn't found.
     */
    fun position(listLink: CPointer<GList>?): Int = g_list_position(_gListPtr, listLink)

    /**
     * Gets the position of the element containing the given data (starting from 0).
     * @param data The data to find.
     * @return The index of the element containing the data, or *-1* if the data isn't found.
     */
    fun index(data: CPointer<*>?): Int = g_list_index(_gListPtr, data)
}

fun doublyLinkedList(listPtr: CPointer<GList>? = null, init: DoublyLinkedList.() -> Unit): DoublyLinkedList {
    val list = DoublyLinkedList(listPtr)
    list.init()
    return list
}
