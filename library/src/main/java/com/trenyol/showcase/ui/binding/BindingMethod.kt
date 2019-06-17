package com.trenyol.showcase.ui.binding

import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(value = [
    BindingMethod(
        type = android.widget.ImageView::class,
        attribute = "app:tint",
        method = "setImageTintList")])
class BindingMethod
