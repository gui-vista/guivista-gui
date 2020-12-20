package org.guiVista.gui.widget.display

import gtk3.*
import kotlinx.cinterop.*
import org.guiVista.gui.widget.WidgetBase

public actual class Image(imagePtr: CPointer<GtkImage>? = null) : WidgetBase {
    override val gtkWidgetPtr: CPointer<GtkWidget>? = imagePtr?.reinterpret() ?: gtk_image_new()
    public val gtkImagePtr: CPointer<GtkImage>?
        get() = gtkWidgetPtr?.reinterpret()

    /**
     * Gets the type of representation being used by the GtkImage to store image data. If the GtkImage has no image
     * data then the return value will be **GtkImageType.GTK_IMAGE_EMPTY**. Default value is
     * *GtkImageType.GTK_IMAGE_EMPTY*.
     */
    public val storageType: GtkImageType
        get() = gtk_image_get_storage_type(gtkImagePtr)
    public actual var pixelSize: Int
        get() = gtk_image_get_pixel_size(gtkImagePtr)
        set(value) {
            if (value >= -1) gtk_image_set_pixel_size(gtkImagePtr, value)
        }

    public actual infix fun changeFile(file: String) {
        gtk_image_set_from_file(gtkImagePtr, file)
    }

    /**
     * Uses the [icon name][iconName] to display a icon in the [image][Image] from the current icon theme. If the
     * [icon name][iconName] isn’t known a “broken image” icon will be displayed instead. If the current icon theme is
     * changed, the icon will be updated appropriately.
     */
    public fun changeIconName(iconName: String, iconSize: GtkIconSize) {
        gtk_image_set_from_icon_name(image = gtkImagePtr, icon_name = iconName, size = iconSize)
    }

    /**
     * Gets the icon name, and the icon size.
     * @return A Pair instance with the first element being the icon name, and the last element being the icon size.
     */
    public fun fetchIconName(): Pair<String, GtkIconSize> = memScoped {
        val iconName = alloc<CPointerVar<ByteVar>>()
        val iconSize = alloc<GtkIconSize.Var>()
        gtk_image_get_icon_name(image = gtkImagePtr, icon_name = iconName.ptr, size = iconSize.ptr)
        (iconName.value?.toKString() ?: "") to iconSize.value
    }

    public actual fun clear() {
        gtk_image_clear(gtkImagePtr)
    }
}

public fun imageWidget(imagePtr: CPointer<GtkImage>? = null, init: Image.() -> Unit): Image {
    val image = Image(imagePtr)
    image.init()
    return image
}
