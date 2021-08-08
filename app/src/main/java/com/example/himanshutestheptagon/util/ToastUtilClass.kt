package com.example.himanshutestheptagon.util

import android.content.Context
import android.widget.Toast

fun showNormalToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}