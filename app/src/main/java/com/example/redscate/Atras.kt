package com.example.redscate

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout


class Atras @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var onBackClickListener: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_atras, this, true)
        val button: ImageView = findViewById(R.id.atras)

        button.setOnClickListener {
            onBackClickListener?.invoke()
        }
    }
}

