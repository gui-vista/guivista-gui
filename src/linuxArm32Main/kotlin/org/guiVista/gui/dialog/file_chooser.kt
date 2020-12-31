package org.guiVista.gui.dialog

import glib2.FALSE
import glib2.TRUE
import gtk3.*
import kotlinx.cinterop.CPointer
import org.guiVista.gui.FileFilter
import org.guiVista.gui.widget.Widget
import org.guiVista.gui.widget.WidgetBase

public actual interface FileChooser {
    public val gtkFileChooserPtr: CPointer<GtkFileChooser>?

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
    public var userPreviewLabel: Boolean
        get() = gtk_file_chooser_get_use_preview_label(gtkFileChooserPtr) == TRUE
        set(value) = gtk_file_chooser_set_use_preview_label(gtkFileChooserPtr, if (value) TRUE else FALSE)
}
