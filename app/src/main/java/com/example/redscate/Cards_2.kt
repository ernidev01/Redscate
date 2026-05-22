package com.example.redscate

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.LAYOUT_INFLATER_SERVICE
import androidx.constraintlayout.widget.ConstraintLayout

class Cards_2(private val activity: Activity) {

    private val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val popupView = inflater.inflate(R.layout.activity_template_cards_2, null)

    private val blurBackground = activity.findViewById<View>(R.id.blur_background)

    private val titulo = popupView.findViewById<TextView>(R.id.title_cards)
    private val buttonAtras = popupView.findViewById<ImageView>(R.id.button_atras)
    private val buttonFinalizar = popupView.findViewById<TextView>(R.id.button_cerrar_2)
    private val buttonClose = popupView.findViewById<ImageView>(R.id.button_cerrar)
    private val buttonSiguiente = popupView.findViewById<ImageView>(R.id.button_siguiente)


    private val textoP = popupView.findViewById<TextView>(R.id.text_2)

    private val subTitulo1 = popupView.findViewById<TextView>(R.id.subtitulo_1)
    private val texto1 = popupView.findViewById<TextView>(R.id.texto_1)
    private val imagen1 = popupView.findViewById<ImageView>(R.id.image_1)

    private val subTitulo2 = popupView.findViewById<TextView>(R.id.subtitulo_2)
    private val texto2 = popupView.findViewById<TextView>(R.id.texto_2)
    private val imagen2 = popupView.findViewById<ImageView>(R.id.image_2)

    private val text = popupView.findViewById<ConstraintLayout>(R.id.text)
    private val imagen_2 = popupView.findViewById<ConstraintLayout>(R.id.imagen_2)


    private var botonSecundario = 1

    fun showPopupWindowReaccionTrabajo(view: View) {
        botonSecundario = 1
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            isFocusable = true
            setOnDismissListener { blurBackground.visibility = View.GONE }
        }
        if (botonSecundario == 1){
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE
            buttonAtras.visibility = View.GONE
        }
        blurBackground.visibility = View.VISIBLE
        updatePopupContentReaccionTrabajo()

        buttonClose.setOnClickListener {
            botonSecundario = 1
            popupWindow.dismiss()
        }

        buttonAtras.setOnClickListener {

            if (botonSecundario > 1) botonSecundario--
            updatePopupContentReaccionTrabajo()
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE

        }

        buttonSiguiente.setOnClickListener {
            botonSecundario++
            if (botonSecundario == 2) {
                buttonSiguiente.visibility = View.GONE
                buttonFinalizar.visibility = View.VISIBLE

            }
            buttonFinalizar.setOnClickListener {
                botonSecundario++
                if (botonSecundario == 2) {
                    buttonSiguiente.visibility = View.GONE
                    buttonFinalizar.visibility = View.VISIBLE

                }
                if (botonSecundario == 3) {
                    popupWindow.dismiss()
                    botonSecundario = 1
                    buttonFinalizar.visibility = View.GONE
                    return@setOnClickListener
                }
            }


            updatePopupContentReaccionTrabajo()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun updatePopupContentReaccionTrabajo() {
        buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

        val contenido = when (botonSecundario) {
            1 -> {
                imagen1.setImageResource(R.drawable.r_trabajo_1)
                imagen2.setImageResource(R.drawable.r_trabajo_2)
                PopupContent(
                textoP = "Aquí encontrarás una breve descripción de cada reacción ante un incendio en tu trabajo y cómo actuar de la mejor manera.",
                subTitulo1 = "Humo en el aire",
                texto1 = "Si hay humo, arrástrate por el suelo y cubre boca y nariz con un trapo húmedo." ,
                subTitulo2 = "No elevadores",
                texto2 = "No uses los ascensores en emergencias; usa siempre las escaleras para evacuar el edificio."
            )
            }

            2 -> {
                imagen1.setImageResource(R.drawable.r_trabajo_3)
                imagen2.setImageResource(R.drawable.r_trabajo_4)
                PopupContent(
                textoP = "",
                subTitulo1 = "Cuidado con las quemaduras",
                texto1 = "Antes de abrir una puerta, toca la manija; si está caliente, podría haber fuego del otro lado." ,
                subTitulo2 = "Da voz de alerta",
                texto2 = "Si ves humo en un pasillo del trabajo, alerta de inmediato a todas las personas."
                )
            }
            else -> return
        }

        titulo.text = "Reacción en trabajo"
        textoP.text = contenido.textoP
        subTitulo1.text = contenido.subTitulo1
        texto1.text = contenido.texto1
        subTitulo2.text = contenido.subTitulo2
        texto2.text = contenido.texto2
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }

    private data class PopupContent(
        val textoP: String="" ,
        val subTitulo1: String="",
        val texto1: String="",

        val subTitulo2: String="",
        val texto2: String=""
    )
    fun showPopupWindowTiposFuego(view: View) {
        botonSecundario = 1
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            isFocusable = true
            setOnDismissListener { blurBackground.visibility = View.GONE }
        }
        if (botonSecundario == 1){
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE
            buttonAtras.visibility = View.GONE
        }
        blurBackground.visibility = View.VISIBLE
        updatePopupWindowTiposFuego()

        buttonClose.setOnClickListener {
            botonSecundario = 1
            popupWindow.dismiss()
        }

        buttonAtras.setOnClickListener {

            if (botonSecundario > 1) botonSecundario--
            updatePopupWindowTiposFuego()
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE

        }

        buttonSiguiente.setOnClickListener {
            botonSecundario++
            if (botonSecundario == 3) {
                buttonSiguiente.visibility = View.GONE
                buttonFinalizar.visibility = View.VISIBLE

            }
            buttonFinalizar.setOnClickListener {
                botonSecundario++
                if (botonSecundario == 3) {
                    buttonSiguiente.visibility = View.GONE
                    buttonFinalizar.visibility = View.VISIBLE

                }
                if (botonSecundario == 4) {
                    popupWindow.dismiss()
                    botonSecundario = 1
                    buttonFinalizar.visibility = View.GONE
                    return@setOnClickListener
                }
            }


            updatePopupWindowTiposFuego()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun updatePopupWindowTiposFuego() {
        buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

        val contenido = when (botonSecundario) {
            1 -> {

                imagen1.setImageResource(R.drawable.t_extintores_1)
                imagen2.setImageResource(R.drawable.t_extintores_2)
                text.visibility=View.VISIBLE
                imagen_2.visibility=View.VISIBLE
                PopupContent(
                    textoP = "Aquí encontrarás los distintos tipos de extintores, cada uno útil para diferentes clases de incendios: A, B, C, D y K.",
                    subTitulo1 = "Clase A",
                    texto1 = "Se usa para combustibles como madera, cartón o plástico. \n" +
                            "Identificado con el color verde." ,
                    subTitulo2 = "Clase B",
                    texto2 = "Se usa para combustibles como aceite, gasolina o pintura.\n" +
                            "Identificado con el color rojo."
                )
            }
            2 -> {
                imagen1.setImageResource(R.drawable.t_extintores_3)
                imagen2.setImageResource(R.drawable.t_extintores_4)
                text.visibility=View.VISIBLE
                imagen_2.visibility=View.VISIBLE
                PopupContent(
                    textoP = "",
                    subTitulo1 = "Clase C",
                    texto1 = "Se usa para combustibles como Brutano, Propano o Gas natural.\n" +
                            "Identificado con el color azul." ,
                    subTitulo2 = "Clase D",
                    texto2 = "Se usa para combustibles como Magnesio, Sodio o Aluminio.\n" +
                            "Identificado con el color amarillo."
                )
            }
            3 ->{
                imagen1.setImageResource(R.drawable.t_extintores_5)
                text.visibility=View.GONE
                imagen_2.visibility=View.GONE
                PopupContent(
                    textoP = "",
                    subTitulo1 = "Clase k",
                    texto1 = "Se usa en aparatos de cocina que usan aceites y grasas.\n" +
                            "Identificado con el color negro."
                 )
            }
            else -> return
        }

        titulo.text = "Tipos de extintores"
        textoP.text = contenido.textoP
        subTitulo1.text = contenido.subTitulo1
        texto1.text = contenido.texto1
        subTitulo2.text = contenido.subTitulo2
        texto2.text = contenido.texto2
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }

}
