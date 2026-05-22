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

class Cards_3(private val activity: Activity) {

    private val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val popupView = inflater.inflate(R.layout.activity_template_cards_1, null)

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

    private val subTitulo = popupView.findViewById<TextView>(R.id.subtitle)
    private val texto = popupView.findViewById<TextView>(R.id.texts_cards)
    private val imagen3 = popupView.findViewById<ImageView>(R.id.images_cards)

    private val template_cards_1 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_1)
    private val template_cards_2 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_2)

    private var botonSecundario = 1

    private data class PopupContent(
        val texto: String = "",
        val subTitulo: String = "",
        val textoP: String = "",
        val subTitulo1: String = "",
        val texto1: String = "",
        val subTitulo2: String = "",
        val texto2: String = ""
    )


    fun showPopupWindoKitEmergencia(view: View) {
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
        if (botonSecundario == 1) {
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE
            buttonAtras.visibility = View.GONE
        }
        blurBackground.visibility = View.VISIBLE
        updatePopupWindoKitEmergencia()


        buttonClose.setOnClickListener {
            botonSecundario = 1
            template_cards_1.visibility = View.VISIBLE
            template_cards_2.visibility = View.GONE
            popupWindow.dismiss()
        }

        buttonAtras.setOnClickListener {

            if (botonSecundario > 1) botonSecundario--
            updatePopupWindoKitEmergencia()
            buttonFinalizar.visibility = View.GONE
            buttonSiguiente.visibility = View.VISIBLE

        }

        buttonSiguiente.setOnClickListener {

            botonSecundario++
            if (botonSecundario == 4) {
                buttonSiguiente.visibility = View.GONE
                buttonFinalizar.visibility = View.VISIBLE

            }
            buttonFinalizar.setOnClickListener {
                botonSecundario++
                if (botonSecundario == 4) {
                    buttonSiguiente.visibility = View.GONE
                    buttonFinalizar.visibility = View.VISIBLE

                }
                if (botonSecundario == 5) {
                    popupWindow.dismiss()
                    botonSecundario = 1
                    buttonFinalizar.visibility = View.GONE
                    return@setOnClickListener
                }
            }

            updatePopupWindoKitEmergencia()
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

    }

    private fun updatePopupWindoKitEmergencia() {

        buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

        val contenido = when (botonSecundario) {
            1 -> {
                imagen1.setImageResource(R.drawable.k_1)
                template_cards_1.visibility = View.VISIBLE
                template_cards_2.visibility = View.GONE
                PopupContent(
                    texto = "Un kit es clave para estar preparado ante desastres o imprevistos. Aquí tienes una lista con los elementos más importantes.",
                    subTitulo = ""
                    //imagen.setImageResource(R.drawable.reaccion_cuidad_image_1)

                )
            }

            2 -> {
                imagen2.setImageResource(R.drawable.k_2)
                imagen3.setImageResource(R.drawable.k_3)

                template_cards_1.visibility = View.GONE
                template_cards_2.visibility = View.VISIBLE

                PopupContent(
                    textoP = "",
                    subTitulo1 = "Botiquín",
                    texto1 = "Incluye elementos como vendas, curas, alcohol, botella de agua y gazas.",
                    subTitulo2 = "Documentos",
                    texto2 = "Guarda una copia de tus documentos en caso de una evacuación rápida."
                )

            }
            3 -> {
                imagen2.setImageResource(R.drawable.k_4)
                imagen3.setImageResource(R.drawable.k_5)
                template_cards_1.visibility = View.GONE
                template_cards_2.visibility = View.VISIBLE

                PopupContent(
                    textoP = "",
                    subTitulo1 = "Linterna",
                    texto1 = "Trae una contigo por si te desplazas por una zona de poca luz.",
                    subTitulo2 = "Pilas / baterías",
                    texto2 = "Te ayudarán en caso de llevar linterna, radio u otros dispositivos."
                )

            }
            4 -> {
                imagen2.setImageResource(R.drawable.k_6)
                imagen3.setImageResource(R.drawable.k_7)
                template_cards_1.visibility = View.GONE
                template_cards_2.visibility = View.VISIBLE

                PopupContent(
                    textoP = "",
                    subTitulo1 = "Papel higiénico",
                    texto1 = "Te servirá para uso de higiene personal en caso de emergencias que sean prolongadas.",
                    subTitulo2 = "Radio",
                    texto2 = "Podrás escuchar alertas y noticias cuando las comunicaciones via internet fallen."
                )

            }
            else -> return
        }

        titulo.text = "Kit de emergencia"

        texto.text = contenido.texto
        subTitulo.text = contenido.subTitulo

        textoP.text = contenido.textoP
        subTitulo1.text = contenido.subTitulo1
        texto1.text = contenido.texto1
        subTitulo2.text = contenido.subTitulo2
        texto2.text = contenido.texto2
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }
    /*
        fun showPopupWindowReaccionCasa(view: View) {
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
            updatePopupContentReaccionCasa()

            buttonClose.setOnClickListener {
                botonSecundario = 1
                popupWindow.dismiss()
            }

            buttonAtras.setOnClickListener {

                if (botonSecundario > 1) botonSecundario--
                updatePopupContentReaccionCasa()
                buttonFinalizar.visibility = View.GONE
                buttonSiguiente.visibility = View.VISIBLE

            }

            buttonSiguiente.setOnClickListener {
                botonSecundario++
                if (botonSecundario == 4) {
                    buttonSiguiente.visibility = View.GONE
                    buttonFinalizar.visibility = View.VISIBLE

                }
                buttonFinalizar.setOnClickListener {
                    botonSecundario++
                    if (botonSecundario == 4) {
                        buttonSiguiente.visibility = View.GONE
                        buttonFinalizar.visibility = View.VISIBLE

                    }
                    if (botonSecundario == 5) {
                        popupWindow.dismiss()
                        botonSecundario = 1
                        buttonFinalizar.visibility = View.GONE
                        return@setOnClickListener
                    }
                }


                updatePopupContentReaccionCasa()
            }

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        private fun updatePopupContentReaccionCasa() {
            buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

            val contenido = when (botonSecundario) {
                1 -> PopupContent(
                    numero = "1.",
                    texto = "Si estás cocinando y se quema tu comida, trata de colocarle una tapa encima para parar el incendio.",
                    subTitulo = "Evíta que el humo se expanda"
                )
                2 -> PopupContent(
                    numero = "2.",
                    texto = "Ubíca las salidas de emergencia de donde vives para evacuar eficientemente en caso de incendio.",
                    subTitulo = "Salidas de emergencia"
                )
                3 -> PopupContent(
                    numero = "3.",
                    texto = "Dirígete a la zona de menor riesgo donde logres llamar al 123 en cuanto puedas.",
                    subTitulo = "Llamada a emergencias"
                )
                4 -> PopupContent(
                    numero = "4.",
                    texto = "Tapate la boca y nariz con un paño humedo para que no te afecte el humo.",
                    subTitulo = "Protégete del humo"
                )
                else -> return
            }

            titulo.text = "Reacción en casa"
            numero.text = contenido.numero
            texto.text = contenido.texto
            subTitulo.text = contenido.subTitulo
            // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
        }
        //imagen.setImageResource(R.drawable.reaccion_casa_image_2)

     */
}