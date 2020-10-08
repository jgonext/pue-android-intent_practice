package es.pue.intentpractice.presentationlayer.controllers.activities.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EditTextNoKeyboard(context: Context, attrs: AttributeSet): AppCompatEditText(context, attrs) {

    override fun onCheckIsTextEditor(): Boolean {
        return false
    }

}