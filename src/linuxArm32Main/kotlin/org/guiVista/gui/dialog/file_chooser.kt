package org.guiVista.gui.dialog

import glib2.FALSE
import glib2.TRUE
import glib2.gpointer
import gtk3.*
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.Error
import org.guiVista.core.connectGSignal
import org.guiVista.core.dataType.SinglyLinkedList
import org.guiVista.gui.FileFilter
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase
import org.guiVista.io.File

public actual interface FileChooser {
    public val gtkFileChooserPtr: CPointer<GtkFileChooser>?

    /** The current folder of chooser as a [File]. */
    public val currentFolderFile: File
        get() = File.fromFilePtr(gtk_file_chooser_get_current_folder_file(gtkFileChooserPtr))

    /**
     * The [File] that should be previewed in a custom preview Internal function. See [previewUri] property. Returns
     * *null* if no file is selected, or the [File] for the file to preview. Close with [File.close].
     */
    public val previewFile: File?
        get() {
            val ptr = gtk_file_chooser_get_preview_file(gtkFileChooserPtr)
            return if (ptr != null) File.fromFilePtr(ptr) else null
        }

    /** The current name in the file selector. */
    public var currentName: String
        get() = gtk_file_chooser_get_current_name(gtkFileChooserPtr)?.toKString() ?: ""
        set(value) = gtk_file_chooser_set_current_name(gtkFileChooserPtr, value)

    /** The filename to preview. Will return *""* if no file was selected. */
    public val previewFileName: String
        get() = gtk_file_chooser_get_preview_filename(gtkFileChooserPtr)?.toKString() ?: ""

    /** The URI that should be previewed in a custom preview widget. See [previewWidget].  */
    public val previewUri: String
        get() = gtk_file_chooser_get_preview_uri(gtkFileChooserPtr)?.toKString() ?: ""

    /**
     * The type of operation that the file selector is performing. Default value is *GTK_FILE_CHOOSER_ACTION_OPEN*.
     */
    public var action: GtkFileChooserAction
        get() = gtk_file_chooser_get_action(gtkFileChooserPtr)
        set(value) = gtk_file_chooser_set_action(gtkFileChooserPtr, value)

    /**
     * Whether a file chooser that isn't in `GTK_FILE_CHOOSER_ACTION_OPEN` mode will offer the user the option to
     * create new folders. Default value is *true*.
     */
    public var createFolders: Boolean
        get() = gtk_file_chooser_get_create_folders(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_create_folders(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /**
     * Whether a file chooser in `GTK_FILE_CHOOSER_ACTION_SAVE` mode will present an overwrite confirmation dialog if
     * the user selects a file name that already exists. Default value is *false*.
     */
    public var doOverwriteConfirmation: Boolean
        get() = gtk_file_chooser_get_do_overwrite_confirmation(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_do_overwrite_confirmation(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /** Application supplied widget for extra options. */
    public var extraWidget: WidgetBase?
        get() {
            val ptr = gtk_file_chooser_get_extra_widget(gtkFileChooserPtr)
            return if (ptr != null) Widget(ptr) else null
        }
        set(value) = gtk_file_chooser_set_extra_widget(gtkFileChooserPtr, value?.gtkWidgetPtr)

    /** The current filter for selecting which files are displayed. */
    public var filter: FileFilter?
        get() {
            val ptr = gtk_file_chooser_get_filter(gtkFileChooserPtr)
            return if (ptr != null) FileFilter(ptr) else null
        }
        set(value) = gtk_file_chooser_set_filter(gtkFileChooserPtr, value?.gtkFileFilterPtr)

    /** Whether the selected file(s) should be limited to local file: URLs. Default value is *true*. */
    public var localOnly: Boolean
        get() = gtk_file_chooser_get_local_only(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_local_only(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /** Application supplied widget for custom previews. */
    public var previewWidget: WidgetBase?
        get() {
            val ptr = gtk_file_chooser_get_preview_widget(gtkFileChooserPtr)
            return if (ptr != null) Widget(ptr) else null
        }
        set(value) = gtk_file_chooser_set_preview_widget(gtkFileChooserPtr, value?.gtkWidgetPtr)

    /** Whether the application supplied widget for custom previews should be shown. Default value is *true*. */
    public var previewWidgetActive: Boolean
        get() = gtk_file_chooser_get_preview_widget_active(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_preview_widget_active(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /** Whether to allow multiple files to be selected. Default value is *false*. */
    public var selectMultiple: Boolean
        get() = gtk_file_chooser_get_select_multiple(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_select_multiple(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /** Whether the hidden files and folders should be displayed. Default value is *false*. */
    public var showHidden: Boolean
        get() = gtk_file_chooser_get_show_hidden(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_show_hidden(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /** Whether to display a stock label with the name of the previewed file. Default value is *true*. */
    public var usePreviewLabel: Boolean
        get() = gtk_file_chooser_get_use_preview_label(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_use_preview_label(gtkFileChooserPtr, if (value) TRUE else FALSE)

    /**
     * Gets the filename for the currently selected file in the file selector. The filename is returned as an absolute
     * path. If multiple files are selected, one of the filenames will be returned at random. If the file chooser is in
     * folder mode then this function returns the selected folder.
     * @return The currently selected filename, or *""* if no file is selected, or the selected file can't be
     * represented with a local filename.
     */
    public fun fetchFileName(): String = gtk_file_chooser_get_filename(gtkFileChooserPtr)?.toKString() ?: ""

    /**
     * Sets filename as the current filename for the file chooser, by changing to the file’s parent folder and actually
     * selecting the file in list; all other files will be unselected. If the chooser is in
     * `GTK_FILE_CHOOSER_ACTION_SAVE` mode then the file’s base name will also appear in the dialog’s file name entry.
     *
     * Note that the file **MUST** exist or nothing will be done except for the directory change. You should use this
     * function only when implementing a save dialog for which you already have a file name to which the user may save.
     * For example when the user opens an existing file, and then does Save As... to save a copy, or a modified version.
     * @param fileName The filename to set as current.
     * @return A value of *true* if the filename has been changed.
     */
    public fun changeFileName(fileName: String): Boolean =
        gtk_file_chooser_set_filename(gtkFileChooserPtr, fileName) == TRUE

    /**
     * Selects a filename. If the file name isn’t in the current folder of chooser, then the current folder of chooser
     * will be changed to the folder containing the filename.
     * @param fileName The filename to set.
     * @return A value of *true* if the filename has been selected.
     */
    public fun selectFileName(fileName: String): Boolean =
        gtk_file_chooser_select_filename(gtkFileChooserPtr, fileName) == TRUE

    /**
     * Unselects a currently selected filename. If the filename is not in the current directory, does not exist, or is
     * otherwise not currently selected, does nothing.
     * @param fileName The filename to unselect.
     */
    public fun unselectFileName(fileName: String) {
        gtk_file_chooser_unselect_filename(gtkFileChooserPtr, fileName)
    }

    /** Selects all the files in the current folder of a file chooser. */
    public fun selectAll() {
        gtk_file_chooser_select_all(gtkFileChooserPtr)
    }

    /** Unselects all the files in the current folder of a file chooser. */
    public fun unselectAll() {
        gtk_file_chooser_unselect_all(gtkFileChooserPtr)
    }

    /**
     * Lists all the selected files and sub folders in the current folder of the [FileChooser]. The returned names are
     * full absolute paths. If files in the current folder cannot be represented as local filenames they will be
     * ignored. (See gtk_file_chooser_get_uris())
     */
    public fun fetchFileNames(): SinglyLinkedList =
        SinglyLinkedList(gtk_file_chooser_get_filenames(gtkFileChooserPtr))

    /**
     * Connects the *confirm-overwrite* event to a [handler] on a [FileChooser]. This event occurs when it is
     * appropriate to present a confirmation dialog when the user has selected a file name that already exists. Note
     * the event only gets emitted when the file chooser is in `GTK_FILE_CHOOSER_ACTION_SAVE` mode.
     *
     * Most applications just need to enable the [doOverwriteConfirmation] property, and they will automatically get a
     * stock confirmation dialog. Applications which need to customize this behavior should do that, and also connect
     * to the **confirm-overwrite** event. A event handler for this event **MUST** return a
     * `GtkFileChooserConfirmation` value, which indicates the action to take. If the handler determines that the user
     * wants to select a different filename, it should return `GTK_FILE_CHOOSER_CONFIRMATION_SELECT_AGAIN`. However if
     * it determines that the user is satisfied with his choice of file name, it should return
     * `GTK_FILE_CHOOSER_CONFIRMATION_ACCEPT_FILENAME`. On the other hand, if it determines that the stock confirmation
     * dialog should be used then it should return GTK_FILE_CHOOSER_CONFIRMATION_CONFIRM.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectConfirmOverwriteEvent(handler: CPointer<ConfirmOverwriteHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkFileChooserPtr, signal = FileChooserEvent.confirmOverwrite, slot = handler, data = userData)
            .toULong()

    /**
     * Connects the *current-folder-changed* event to a [handler] on a [FileChooser]. This event occurs when the current
     * folder in a [FileChooser] changes. This can happen due to the user performing some action that changes folders,
     * such as selecting a bookmark or visiting a folder on the file list. It can also happen as a result of calling a
     * function to explicitly change the current folder in a file chooser.
     *
     * Normally you do not need to connect to this event, unless you need to keep track of which folder a file chooser
     * is showing.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectCurrentFolderChangedEvent(handler: CPointer<CurrentFolderChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkFileChooserPtr, signal = FileChooserEvent.currentFolderChanged, slot = handler, data = userData)
            .toULong()

    /**
     * Connects the *file-activated* event to a [handler] on a [FileChooser]. This event occurs when the user
     * "activates" a file in the file chooser. This can happen by double-clicking on a file in the file list, or by
     * pressing **Enter**. Normally you do not need to connect to this event. It is used internally by
     * [GtkFileChooserDialog] to know when to activate the default button in the dialog.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectFileActivatedEvent(handler: CPointer<FileActivatedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkFileChooserPtr, signal = FileChooserEvent.fileActivated, slot = handler, data = userData).toULong()

    /**
     * Connects the *selection-changed* event to a [handler] on a [FileChooser]. This event occurs when there is a
     * change in the set of selected files in a [FileChooser]. This can happen when the user modifies the selection
     * with the mouse or the keyboard, or when explicitly calling functions to change the selection.
     *
     * Normally you do not need to connect to this event as it is easier to wait for the file chooser to finish
     * running, and then to get the list of selected files using the functions mentioned below.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectSelectionChangedEvent(handler: CPointer<SelectionChangedHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkFileChooserPtr, signal = FileChooserEvent.selectionChanged, slot = handler, data = userData)
            .toULong()

    /**
     * Connects the *update-preview* event to a [handler] on a [FileChooser]. This event occurs when the preview in a
     * file chooser should be regenerated. For example this can happen when the currently selected file changes. You
     * should use this event if you want your file chooser to have a preview widget. Once you have installed a preview
     * widget with [previewWidget], you should update it when this event is emitted. You can use the functions
     * `gtk_file_chooser_get_preview_filename()`, or `gtk_file_chooser_get_preview_uri()` to get the name of the file
     * to preview. Your widget may not be able to preview all kinds of files; your callback **MUST** set the
     * [previewWidgetActive] property to inform the file chooser about whether the preview was generated successfully
     * or not.
     * @param handler The event handler for the event.
     * @param userData User data to pass through to the [handler].
     */
    public fun connectUpdatePreviewEvent(handler: CPointer<UpdatePreviewHandler>, userData: gpointer): ULong =
        connectGSignal(obj = gtkFileChooserPtr, signal = FileChooserEvent.updatePreview, slot = handler, data = userData).toULong()

    /**
     * Sets the current folder for chooser from a local filename. The user will be shown the full contents of the
     * current folder, plus user interface elements for navigating to other folders.
     * @param fileName The full path of the new current folder.
     * @return A value of *true* if the current folder has been set.
     */
    public fun changeCurrentFolder(fileName: String): Boolean =
        gtk_file_chooser_set_current_folder(gtkFileChooserPtr, fileName) == TRUE

    /**
     * Gets the current folder of chooser as a local filename. Note that this is the folder that the file chooser is
     * currently displaying (e.g. "/home/username/Documents"), which is not the same as the currently selected folder
     * if the chooser is in `GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER` mode (e.g.
     * /home/username/Documents/selected-folder/". To get the currently selected folder in that mode, use
     * `gtk_file_chooser_get_uri()` as the usual way to get the selection.
     * @return The full path of the current folder, or *""* if the current path cannot be represented as a local
     * filename. This function will also return *""* if the file chooser was unable to load the last folder that was
     * requested from it; for example, as would be for calling [changeCurrentFolder] on a nonexistent folder.
     * @see changeCurrentFolder
     */
    public fun fetchCurrentFolder(): String = gtk_file_chooser_get_current_folder(gtkFileChooserPtr)?.toKString() ?: ""

    /**
     * Gets the URI for the currently selected file in the file selector. If multiple files are selected, one of the
     * filenames will be returned at random. If the file chooser is in folder mode then this function returns the
     * selected folder.
     * @return The currently selected URI, or *""* if no file is selected. If `gtk_file_chooser_set_local_only()` is
     * set to TRUE (the default) a local URI will be returned for any FUSE locations.
     */
    public fun fetchUri(): String = gtk_file_chooser_get_uri(gtkFileChooserPtr)?.toKString() ?: ""

    /**
     * Sets the file referred to by uri as the current file for the file chooser by changing to the URI’s parent
     * folder, and actually selecting the URI in the list. If the chooser is `GTK_FILE_CHOOSER_ACTION_SAVE` mode then
     * the URI’s base name will also appear in the dialog’s file name entry. Note that the URI **MUST** exist, or
     * nothing will be done except for the directory change.
     *
     * You should use this function only when implementing a save dialog for which you already have a file name to
     * which the user may save. For example when the user opens an existing file, and then does Save As... to save a
     * copy or a modified version.
     * @param uri The uri to set as current.
     * @return A value of *true* if the uri has been set.
     */
    public fun changeUri(uri: String): Boolean = gtk_file_chooser_set_uri(gtkFileChooserPtr, uri) == TRUE

    /**
     * Selects the file to by uri. If the URI doesn’t refer to a file in the current folder of chooser, then the
     * current folder of chooser will be changed to the folder containing filename.
     * @param uri The uri to select.
     * @return A value of *true* if the uri has been selected.
     */
    public fun selectUri(uri: String): Boolean = gtk_file_chooser_select_uri(gtkFileChooserPtr, uri) == TRUE

    /**
     * Unselects the file referred to by uri. If the file is not in the current directory, does not exist, or is
     * otherwise not currently selected, does nothing.
     * @param uri The uri to unselect.
     */
    public fun unselectUri(uri: String) {
        gtk_file_chooser_unselect_uri(gtkFileChooserPtr, uri)
    }

    /**
     * Lists all the selected files and sub folders in the current folder of chooser. The returned names are full
     * absolute URIs.
     * @return A list containing the URIs of all selected files, and sub folders in the current folder. Remember to
     * [close the list][SinglyLinkedList.close] afterwards.
     */
    public fun fetchMultipleUris(): SinglyLinkedList =
        SinglyLinkedList(gtk_file_chooser_get_uris(gtkFileChooserPtr))

    /**
     * Sets the current folder for chooser from an URI. The user will be shown the full contents of the current folder,
     * plus user interface elements for navigating to other folders.
     * @param uri The URI for the new current folder.
     * @return A value of *true* if the folder could be changed successfully.
     */
    public fun changeCurrentFolderUri(uri: String): Boolean =
        gtk_file_chooser_set_current_folder_uri(gtkFileChooserPtr, uri) == TRUE

    /**
     * Gets the current folder of chooser as an URI. Note that this is the folder that the file chooser is currently
     * displaying (e.g. "file:///home/username/Documents"), which is not the same as the currently-selected folder if
     * the chooser is in `GTK_FILE_CHOOSER_ACTION_SELECT_FOLDER` mode (e.g.
     * "file:///home/username/Documents/selected-folder/". To get the currently selected folder in that mode, use
     * [fetchUri] as the usual way to get the selection.
     * @see changeCurrentFolderUri
     */
    public fun fetchCurrentFolderUri(): String =
        gtk_file_chooser_get_current_folder_uri(gtkFileChooserPtr)?.toKString() ?: ""

    /**
     * Adds [filter] to the list of filters that the user can select between. When a filter is selected, only files that
     * are passed by that filter are displayed. Note that the chooser takes ownership of the filter, so you have to
     * ref and sink it if you want to keep a reference.
     * @param filter A file filter.
     */
    public infix fun addFilter(filter: FileFilter) {
        gtk_file_chooser_add_filter(gtkFileChooserPtr, filter.gtkFileFilterPtr)
    }

    /**
     * Removes [filter] from the list of filters that the user can select between.
     * @param filter A file filter.
     */
    public infix fun removeFilter(filter: FileFilter) {
        gtk_file_chooser_remove_filter(gtkFileChooserPtr, filter.gtkFileFilterPtr)
    }

    /**
     * Lists the current set of user selectable filters.
     * @see addFilter
     * @see removeFilter
     * @return A list containing the current set of user selectable filters. The contents of the list are owned by
     * GTK+, but you must close the list itself with [SinglyLinkedList.close] when you are done with it.
     */
    public fun listFilters(): SinglyLinkedList = SinglyLinkedList(gtk_file_chooser_list_filters(gtkFileChooserPtr))

    /**
     * Adds a folder to be displayed with the shortcut folders in a file chooser. Note that shortcut folders do not get
     * saved, as they are provided by the application. For example you can use this to add a
     * “/usr/share/mydrawprogram/Clipart” folder to the volume list.
     * @param folder The filename of the folder to add.
     * @param error The location to store error, or *null*.
     * @return A value of *true* if the folder could be added successfully, *false* otherwise. In the latter case the
     * error will be set as appropriate.
     */
    public fun addShortcutFolder(folder: String, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_add_shortcut_folder(
            chooser = gtkFileChooserPtr,
            folder = folder,
            error = null
        ) == TRUE
    }

    /**
     * Removes a folder from a file chooser’s list of shortcut folders.
     * @param folder The filename of the folder to remove.
     * @param error The location to store error, or *null*.
     * @return A value of *true* if the operation succeeds, *false* otherwise. In the latter case, the error will be
     * set as appropriate.
     * @see addShortcutFolder
     */
    public fun removeShortcutFolder(folder: String, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_remove_shortcut_folder(
            chooser = gtkFileChooserPtr,
            folder = folder,
            error = null
        ) == TRUE
    }

    /**
     * Queries the list of shortcut folders in the file chooser as set by [addShortcutFolder].
     * @return A list of folder filenames, or *null* if there are no shortcut folders. Close the returned list with
     * [SinglyLinkedList.close].
     */
    public fun listShortcutFolders(): SinglyLinkedList? {
        val ptr = gtk_file_chooser_list_shortcut_folders(gtkFileChooserPtr)
        return if (ptr != null) SinglyLinkedList(ptr) else null
    }

    /**
     * Adds a folder URI to be displayed with the shortcut folders in a file chooser. Note that shortcut folders do not
     * get saved, as they are provided by the application. For example you can use this to add a
     * “file:///usr/share/mydrawprogram/Clipart” folder to the volume list.
     * @param uri URI of the folder to add.
     * @param error The location to store error, or *null*.
     * @return A value of *true* if the folder could be added successfully, *false* otherwise. In the latter case the
     * error will be set as appropriate.
     */
    public fun addShortcutFolderUri(uri: String, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_add_shortcut_folder_uri(chooser = gtkFileChooserPtr, error = null, uri = uri) == TRUE
    }

    /**
     * Removes a folder URI from a file chooser’s list of shortcut folders.
     * @param uri URI of the folder to remove.
     * @param error The location to store error, or *null*.
     * @return A value of *true* if the operation succeeds, *false* otherwise. In the latter case the error will be set
     * as appropriate.
     * @see addShortcutFolderUri
     */
    public fun removeShortcutFolderUri(uri: String, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_remove_shortcut_folder_uri(chooser = gtkFileChooserPtr, error = null, uri = uri) == TRUE
    }

    /**
     * Queries the list of shortcut folders in the file chooser, as set by [addShortcutFolderUri].
     * @return A list of folder URIs, or *null* if there are no shortcut folders. Close the returned list with
     * [SinglyLinkedList.close].
     */
    public fun listAllShortcutFolderUris(): SinglyLinkedList? {
        val ptr = gtk_file_chooser_list_shortcut_folder_uris(gtkFileChooserPtr)
        return if (ptr != null) SinglyLinkedList(ptr) else null
    }

    /**
     * Gets the [File] for the currently selected file in the file selector. If multiple files are selected, one of the
     * files will be returned at random. If the file chooser is in folder mode then this function returns the selected
     * folder.
     * @return A selected [File]. You own the returned file; use [File.close] to close it.
     */
    public fun fetchFile(): File = File.fromFilePtr(gtk_file_chooser_get_file(gtkFileChooserPtr))

    /**
     * Lists all the selected files and sub folders in the current folder of chooser as [File]. An internal function,
     * see `gtk_file_chooser_get_uris()`.
     */
    public fun fetchAllFiles(): SinglyLinkedList = SinglyLinkedList(gtk_file_chooser_get_files(gtkFileChooserPtr))

    /**
     * Selects the file referred to by file. An internal function.
     * @param file The file to select.
     * @param error The location to store error, or *null*.
     * @return A value of *true* if the [file] is selected.
     * @see selectUri
     */
    public fun selectFile(file: File, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_select_file(chooser = gtkFileChooserPtr, file = file.gFilePtr, error = null) == TRUE
    }

    /**
     * Sets the current folder for chooser from a [file]. Internal function.
     * @param file The file for the new folder
     * @param error The location to store error, or *null*.
     * @return A value of *true* if the folder could be changed successfully, *false* otherwise.
     * @see changeCurrentFolderUri
     */
    public fun changeCurrentFolderFile(file: File, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_set_current_folder_file(
            chooser = gtkFileChooserPtr,
            error = null,
            file = file.gFilePtr
        ) == TRUE
    }

    /**
     * Sets file as the current filename for the file chooser, by changing to the file’s parent folder and actually
     * selecting the file in the list. If the chooser is in `GTK_FILE_CHOOSER_ACTION_SAVE` mode then the file’s base
     * name will also appear in the dialog’s file name entry. If the file name isn’t in the current folder of chooser,
     * then the current folder of chooser will be changed to the folder containing filename. This is equivalent to a
     * sequence of [unselectAll] followed by [selectFileName].
     *
     * Note that the file **MUST** exist, or nothing will be done except for the directory change. If you are
     * implementing a save dialog, you should use this function if you already have a file name to which the user may
     * save; for example, when the user opens an existing file and then does *Save As...*.
     * @param file The file to set as current.
     * @param error The location to store the error, or *null* to ignore errors.
     * @return A value of *true* if the file has been set.
     */
    public fun changeFile(file: File, error: Error? = null): Boolean {
        // TODO: Cover error handling.
        return gtk_file_chooser_set_file(chooser = gtkFileChooserPtr, error = null, file = file.gFilePtr) == TRUE
    }

    /**
     * Unselects the file referred to by file. If the file is not in the current directory, does not exist, or is
     * otherwise not currently selected, does nothing.
     */
    public fun unselectFile(file: File) {
        gtk_file_chooser_unselect_file(gtkFileChooserPtr, file.gFilePtr)
    }
}

/**
 * The event handler for the *confirm-overwrite* event. Arguments:
 * 1. chooser: CPointer<GtkFileChooser>
 * 2. userData: gpointer
 *
 * Returns GtkFileChooserConfirmation.
 */
public typealias ConfirmOverwriteHandler = CFunction<(
    chooser: CPointer<GtkFileChooser>,
    userData: gpointer
) -> GtkFileChooserConfirmation>

/**
 * The event handler for the *file-activated* event. Arguments:
 * 1. chooser: CPointer<GtkFileChooser>
 * 2. userData: gpointer
 */
public typealias FileActivatedHandler = CFunction<(chooser: CPointer<GtkFileChooser>, userData: gpointer) -> Unit>

/**
 * The event handler for the *selection-changed* event. Arguments:
 * 1. chooser: CPointer<GtkFileChooser>
 * 2. userData: gpointer
 */
public typealias SelectionChangedHandler = CFunction<(chooser: CPointer<GtkFileChooser>, userData: gpointer) -> Unit>

/**
 * The event handler for the *current-folder-changed* event. Arguments:
 * 1. chooser: CPointer<GtkFileChooser>
 * 2. userData: gpointer
 */
public typealias CurrentFolderChangedHandler = CFunction<(
    chooser: CPointer<GtkFileChooser>,
    userData: gpointer
) -> Unit>

/**
 * The event handler for the *update-preview* event. Arguments:
 * 1. chooser: CPointer<GtkFileChooser>
 * 2. userData: gpointer
 */
public typealias UpdatePreviewHandler = CFunction<(chooser: CPointer<GtkFileChooser>, userData: gpointer) -> Unit>
