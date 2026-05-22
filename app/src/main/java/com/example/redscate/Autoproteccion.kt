package com.example.redscate

import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.LAYOUT_INFLATER_SERVICE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity

class Autoproteccion(private val activity: Activity) {

    private val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val blurBackground = activity.findViewById<View>(R.id.blur_background_autoproteccion)

    private var botonSecundario = 1

    fun showPopupWindowAutoproteccion(view: View) {
        val popupView = inflater.inflate(R.layout.activity_template_autoproteccion, null)

        val auto = popupView.findViewById<ConstraintLayout>(R.id.auto)
        val autoCards = popupView.findViewById<ConstraintLayout>(R.id.auto_cards)
        val buttonSi = popupView.findViewById<TextView>(R.id.button_si)
        val buttonNo = popupView.findViewById<TextView>(R.id.button_no)

        autoCards.visibility = View.GONE
        auto.visibility = View.VISIBLE
        botonSecundario = 1
        blurBackground.visibility = View.VISIBLE

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

        buttonNo.setOnClickListener {
            popupWindow.dismiss()
        }

        buttonSi.setOnClickListener {
            popupWindow.dismiss()
            showPopupWindowAutoproteccionCards(view)
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    fun showPopupWindowAutoproteccionCards(view: View) {
        val popupView = inflater.inflate(R.layout.activity_template_autoproteccion, null)

        val auto = popupView.findViewById<ConstraintLayout>(R.id.auto)
        val autoCards = popupView.findViewById<ConstraintLayout>(R.id.auto_cards)
        val texto = popupView.findViewById<TextView>(R.id.auto_text)
        val imagen = popupView.findViewById<ImageView>(R.id.auto_img)
        val buttonAtras = popupView.findViewById<ImageView>(R.id.button_atras)
        val buttonFinalizar = popupView.findViewById<TextView>(R.id.button_cerrar_2)
        val buttonSiguiente = popupView.findViewById<ImageView>(R.id.button_siguiente)
        val barra = popupView.findViewById<ImageView>(R.id.auto_barra)

        auto.visibility = View.GONE
        autoCards.visibility = View.VISIBLE
        botonSecundario = 1
        blurBackground.visibility = View.VISIBLE

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

        fun updatePopupContentAutoProteccion() {
            buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

            val contenido = when (botonSecundario) {
                1 -> {
                    barra.setImageResource(R.drawable.barra_1)
                    imagen.setImageResource(R.drawable.auto_1)
                    "Huye contra el viento. \n¡Nunca subas colinas!"
                }
                2 -> {
                    barra.setImageResource(R.drawable.barra_2)
                    imagen.setImageResource(R.drawable.auto_2)
                    "¿Hay humo? Cubre nariz \ny boca con tela húmeda."
                }
                3 -> {
                    barra.setImageResource(R.drawable.barra_3)
                    imagen.setImageResource(R.drawable.auto_3)
                    "No corras. Avanza con \ncuidado, pisando firme."
                }
                4 -> {
                    imagen.setImageResource(R.drawable.auto_4)
                    "¡Hazte visible! Usa espejo, \nlinterna o ropa clara."
                }
                else -> return
            }

            texto.text = contenido
        }

        if (botonSecundario == 1) {
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE
            buttonAtras.visibility = View.GONE
        }

        updatePopupContentAutoProteccion()



        buttonAtras.setOnClickListener {
            if (botonSecundario > 1) botonSecundario--
            updatePopupContentAutoProteccion()
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE
        }

        buttonSiguiente.setOnClickListener {
            botonSecundario++
            updatePopupContentAutoProteccion()

            if (botonSecundario == 4) {
                buttonSiguiente.visibility = View.GONE
                barra.visibility = View.GONE
                buttonFinalizar.visibility = View.VISIBLE
            }

            buttonFinalizar.setOnClickListener {
                botonSecundario++
                if (botonSecundario == 5) {
                    popupWindow.dismiss()
                    botonSecundario = 1
                    buttonFinalizar.visibility = View.GONE
                }
            }
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }
}