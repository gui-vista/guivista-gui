package org.guivista.core.widget.display

import gtk3.*
import kotlinx.cinterop.*
import org.guivista.core.widget.WidgetBase

/** A widget displaying an image. */
class Image(imagePtr: CPointer<GtkImage>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = imagePtr?.reinterpret() ?: gtk_image_new()
    val gtkImagePtr: CPointer<GtkImage>?
        get() = gtkWidgetPtr?.reinterpret()
    /**
     * Gets the type of representation being used by the GtkImage to store image data. If the GtkImage has no image
     * data, the return value will be **GTK_IMAGE_EMPTY**.
     */
    val storageType: GtkImageType
        get() = gtk_image_get_storage_type(gtkImagePtr)
    /**
     * Pixel size to use for named icons. If the pixel size is set to a value != **-1**, it is used instead of the
     * icon size set by `gtk_image_set_from_icon_name()`.
     */
    var pixelSize: Int
        get() = gtk_image_get_pixel_size(gtkImagePtr)
        set(value) = gtk_image_set_pixel_size(gtkImagePtr, value)

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
    fun changeFile(file: String) {
        gtk_image_set_from_file(gtkImagePtr, file)
    }

    /**
     * Uses the [icon name][iconName] to display a icon in the [image][Image] from the current icon theme. If the
     * [icon name][iconName] isn’t known a “broken image” icon will be displayed instead. If the current icon theme is
     * changed, the icon will be updated appropriately.
     */
    fun changeIconName(iconName: String, iconSize: GtkIconSize) {
        gtk_image_set_from_icon_name(image = gtkImagePtr, icon_name = iconName, size = iconSize)
    }

    /**
     * Gets the icon name, and the icon size.
     * @return A Pair instance with the first element being the icon name, and the last element being the icon size.
     */
    fun fetchIconName(): Pair<String, GtkIconSize> = memScoped {
        val iconName = alloc<CPointerVar<ByteVar>>()
        val iconSize = alloc<GtkIconSize.Var>()
        gtk_image_get_icon_name(image = gtkImagePtr, icon_name = iconName.ptr, size = iconSize.ptr)
        (iconName.value?.toKString() ?: "") to iconSize.value
    }

    /** Resets the [Image] instance so it doesn't show an image. */
    fun clear() {
        gtk_image_clear(gtkImagePtr)
    }
}

fun imageWidget(imagePtr: CPointer<GtkImage>? = null, init: Image.() -> Unit): Image {
    val image = Image(imagePtr)
    image.init()
    return image
}
