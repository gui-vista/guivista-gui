package org.guiVista.gui.widget.display

import org.guiVista.gui.widget.WidgetBase

/** A widget displaying an image. */
expect class Image : WidgetBase {
    /**
     * Pixel size to use for named icons. If the pixel size is set to a value != **-1**, it is used instead of the
     * icon size set by `gtk_image_set_from_icon_name()`. Default value is *-1*.
     */
    var pixelSize: Int

    /**
     * Uses the [file] which is displayed in the [image][Image]. If the [file] isn’t found or can’t be loaded the resulting
     * GtkImage will display a “broken image” icon. If the file contains an animation the [image][Image] will contain
     * an animation.
     *
     * If you need to detect failures to load the file use `gdk_pixbuf_new_from_file()` to load the file yourself, then
     * create the GtkImage from the pixbuf, or for animations use `gdk_pixbuf_animation_new_from_file()`. The storage
     * type (`gtk_image_get_storage_type()`) of the returned image is not defined. It will be whatever is appropriate
     * for displaying the file.
     *
     * @param file Absolute path to the file (a image) that will be displayed.
     */
    infix fun changeFile(file: String)

    /** Resets the [Image] instance so it doesn't show an image. */
    fun clear()
}
