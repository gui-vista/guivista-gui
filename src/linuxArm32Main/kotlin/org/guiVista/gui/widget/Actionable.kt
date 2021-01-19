package org.guiVista.gui.widget

import gtk3.*
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import org.guiVista.core.dataType.Variant

public actual interface Actionable {
    public val gtkActionablePtr: CPointer<GtkActionable>?

    /**
     * The name of the action with which this widget should be associated. Setting this property with an empty String
     * will unassociate this widget from any previous action.
     */
    public var actionName: String
        get() = gtk_actionable_get_action_name(gtkActionablePtr)?.toKString() ?: ""
        set(value) = gtk_actionable_set_action_name(gtkActionablePtr, if (value.isEmpty()) null else value)

    /** The target value of an actionable widget. Setting this property with a *null* will unset the target value. */
    public var actionTargetValue: Variant?
        get() = Variant.fromPointer(gtk_actionable_get_action_target_value(gtkActionablePtr))
        set(value) = gtk_actionable_set_action_target_value(gtkActionablePtr, value?.gVariantPtr)

    /**
     * Sets the target of an actionable widget. This is a convenience function that creates a [Variant] for
     * [formatStr], and uses the result to set [actionTargetValue]. If you are setting a String valued target, and want
     * to set the action name at the same time, then you can use [changeDetailedActionName].
     * @param formatStr The format String.
     */
    public fun changeActionTarget(formatStr: String) {
        gtk_actionable_set_action_target(gtkActionablePtr, formatStr)
    }

    /**
     * Sets the action name, and associated String target value of an actionable widget. This allows for the effect of
     * both setting [actionName], and [actionTargetValue] in the common case that the target is String valued. Note
     * that [detailedActionName] is a String of the form **action::target** where action is the action name, and target
     * is the String to use as the target.
     */
    public fun changeDetailedActionName(detailedActionName: String) {
        gtk_actionable_set_detailed_action_name(gtkActionablePtr, detailedActionName)
    }
}
