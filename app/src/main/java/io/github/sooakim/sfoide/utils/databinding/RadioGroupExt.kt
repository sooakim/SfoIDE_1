package io.github.sooakim.sfoide.utils.databinding

import android.widget.RadioGroup
import androidx.core.view.children
import androidx.databinding.BindingAdapter

@BindingAdapter("checkedRadioWithTag")
fun <T> RadioGroup.bindCheckedRadio(tag: T?) {
    val selectedButton = this.children.filter { childButton ->
        childButton.tag == tag
    }.firstOrNull()

    if (selectedButton == null) {
        this.clearCheck()
    } else {
        this.check(selectedButton.id)
    }
}