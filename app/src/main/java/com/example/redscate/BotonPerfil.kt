package com.example.redscate

import android.content.Context
import android.content.Intent

import android.util.AttributeSet
import android.view.LayoutInflater

import android.widget.ImageView

import androidx.constraintlayout.widget.ConstraintLayout


class BotonPerfil  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        // Inflamos el layout
        LayoutInflater.from(context).inflate(R.layout.activity_boton_perfil, this, true)

        // Referenciamos el botón
        val button: ImageView = findViewById(R.id.boton_perfil)

        // Configuramos la navegación
        button.setOnClickListener {
            val intent = Intent(context, Perfil::class.java)
            context.startActivity(intent)
        }
    }
}