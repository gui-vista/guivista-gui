package org.guiVista.gui.widget.textEditor

import org.guiVista.gui.layout.Container
import org.guiVista.gui.text.TextBuffer
import org.guiVista.gui.widget.WidgetBase

/** A widget that displays a [org.guiVista.gui.text.TextBuffer]. */
public expect class TextView : Container {
    /** The buffer which is displayed. */
    public var buffer: TextBuffer

    /** Whether the text can be modified by the user. Default value is *true*. */
    public var editable: Boolean

    /** If the insertion cursor is shown. Default value is *true*. */
    public var cursorVisible: Boolean

    /** Whether entered text overwrites existing contents. Default value is *false*. */
    public var overwrite: Boolean

    /** Pixels of blank space above paragraphs. Default value is *0*. */
    public var pixelsAboveLines: Int

    /** Pixels of blank space below paragraphs. Default value is *0*. */
    public var pixelsBelowLines: Int

    /** Pixels of blank space between wrapped lines in a paragraph. Default value is *0*. */
    public var pixelsInsideWrap: Int

    /**
     * The left margin for text in the text view. Note that this property is confusingly named. In CSS terms the
     * value set here is padding, and it is applied in addition to the padding from the theme. Don't confuse this
     * property with **margin-left**.
     */
    public var leftMargin: Int

    /**
     * The right margin for text in the text view. Note that this property is confusingly named. In CSS terms the
     * value set here is padding, and it is applied in addition to the padding from the theme. Don't confuse this
     * property with **margin-right**.
     */
    public var rightMargin: Int

    /**
     * The top margin for text in the text view. Note that this property is confusingly named. In CSS terms the
     * value set here is padding, and it is applied in addition to the padding from the theme. Don't confuse this
     * property with **margin-top**.
     */
    public var topMargin: Int

    /**
     * The bottom margin for text in the text view. Note that this property is confusingly named. In CSS terms the
     * value set here is padding, and it is applied in addition to the padding from the theme. Don't confuse this
     * property with **margin-bottom**.
     */
    public var bottomMargin: Int

    /** Amount to indent the paragraph, in pixels. Default value is *0*. */
    public var indent: Int

    /** Whether Tab will result in a tab character being entered. Default value is *true*. */
    public var acceptsTab: Boolean

    /** Whether to use a monospace font. Default value is *false*. */
    public var monospace: Boolean


    /**
     * Moves the cursor to the currently visible region of the buffer, it it isn’t there already.
     * @return A value of *true* if the cursor had to be moved.
     */
    public fun placeCursorOnscreen(): Boolean

    /**
     * Updates the position of a child, as for gtk_text_view_add_child_in_window().
     * @param child The child widget already added to the text view.
     * @param xPos New X position in window coordinates.
     * @param yPos New X position in window coordinates.
     */
    public fun moveChild(child: WidgetBase, xPos: Int, yPos: Int)

    /**
     * Ensures that the cursor is shown (i.e. not in an **off** blink interval), and resets the time that it will stay
     * blinking (or visible in case blinking is disabled). This function should be called in response to user input
     * (e.g. from derived classes that override the text view's **key-press-event** handler).
     */
    public fun resetCursorBlink()
}