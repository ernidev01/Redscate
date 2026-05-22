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

class Cards_1(private val activity: Activity) {

    private val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val popupView = inflater.inflate(R.layout.activity_template_cards, null)
    private val blurBackground = activity.findViewById<View>(R.id.blur_background)

    private val titulo = popupView.findViewById<TextView>(R.id.title_cards)
    private val subTitulo = popupView.findViewById<TextView>(R.id.subtitle)
    private val numero = popupView.findViewById<TextView>(R.id.number_card)
    private val texto = popupView.findViewById<TextView>(R.id.texts_cards)
    private val imagen = popupView.findViewById<ImageView>(R.id.images_cards)

    private val buttonAtras = popupView.findViewById<ImageView>(R.id.button_atras)
    private val buttonFinalizar = popupView.findViewById<TextView>(R.id.button_cerrar_2)
    private val buttonClose = popupView.findViewById<ImageView>(R.id.button_cerrar)
    private val buttonSiguiente = popupView.findViewById<ImageView>(R.id.button_siguiente)

    private var botonSecundario = 1

    fun showPopupWindowReaccionCampo(view: View) {
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
        updatePopupContentReaccionCampo()

        buttonClose.setOnClickListener {
            botonSecundario = 1
            popupWindow.dismiss()
        }

        buttonAtras.setOnClickListener {

            if (botonSecundario > 1) botonSecundario--
            updatePopupContentReaccionCampo()
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


            updatePopupContentReaccionCampo()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun updatePopupContentReaccionCampo() {
        buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

        val contenido = when (botonSecundario) {
            1 -> {
                imagen.setImageResource(R.drawable.r_campo_1)
                PopupContent(
                    numero = "",
                    texto = "Evita las zonas con pendientes, los filos de montaña y valles estrechos, ya que el aire caliente tiende a ascender.",
                    subTitulo = ""

                )
            }
            2 ->{
                imagen.setImageResource(R.drawable.r_campo_2)
                PopupContent(
                numero = "",
                texto = "Evita refugiarte en pozos o cuevas, ya que el oxigeno se te podría acabar rápidamente.",
                subTitulo = ""
            )}

            3 -> {
                imagen.setImageResource(R.drawable.r_campo_3)
                PopupContent(
                    numero = "",
                    texto = "No realices fogatas ni asados en temporada de verano ya que el pasto estará seco y subirá el riesgo de provocar un incendio forestal.",
                    subTitulo = "Tener en cuenta"
                )
            }
            4 -> {
                imagen.setImageResource(R.drawable.r_campo_4)
                PopupContent(
                    numero = "",
                    texto = "No arrojes las colillas de los cigarrillos en zonas verdes. Esto puede expandir aún más el incendio en caso de que ocurra.",
                    subTitulo = "Tener en cuenta"
                )
            }
            else -> return
        }

        titulo.text = "Reacción en campo"
        numero.text = contenido.numero
        texto.text = contenido.texto
        subTitulo.text = contenido.subTitulo
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }

    private data class PopupContent(
        val numero: String,
        val texto: String,
        val subTitulo: String
    )


    fun showPopupWindowReaccionCiudad(view: View) {
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
        updatePopupContentReaccionCiudad()

        buttonClose.setOnClickListener {
            botonSecundario = 1
            popupWindow.dismiss()
        }

        buttonAtras.setOnClickListener {

            if (botonSecundario > 1) botonSecundario--
            updatePopupContentReaccionCiudad()
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


            updatePopupContentReaccionCiudad()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun updatePopupContentReaccionCiudad() {
        buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

        val contenido = when (botonSecundario) {
            1 -> {
                imagen.setImageResource(R.drawable.r_ciudad_1)
                PopupContent(
                    numero = "",
                    texto = "Dirígete a la zona de menor riesgo donde logres llamar al 123 en cuanto puedas.",
                    subTitulo = "Llamada a emergencias"

                )
            }
            2 -> {
                imagen.setImageResource(R.drawable.r_ciudad_2)
                PopupContent(
                    numero = "",
                    texto = "Da una voz de alerta a las personas que estén cerca del incendio.",
                    subTitulo = "Alerta a la ciudadanía"
                )
            }
            3 -> {
                imagen.setImageResource(R.drawable.r_ciudad_3)
                PopupContent(
                    numero = "",
                    texto = "identifica el objeto que provocó el incendio para informar a las autoridades. (identifícalo sin colocar tu vida en riesgo.)",
                    subTitulo = "Identifica razón del incendio"
                )
            }
            else -> return
        }

        titulo.text = "Reacción en ciudad"
        numero.text = contenido.numero
        texto.text = contenido.texto
        subTitulo.text = contenido.subTitulo
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }

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
        if (botonSecundario == 1) {
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
            if (botonSecundario == 5) {
                buttonSiguiente.visibility = View.GONE
                buttonFinalizar.visibility = View.VISIBLE

            }
            buttonFinalizar.setOnClickListener {
                botonSecundario++
                if (botonSecundario == 5) {
                    buttonSiguiente.visibility = View.GONE
                    buttonFinalizar.visibility = View.VISIBLE

                }
                if (botonSecundario == 6) {
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
            1 -> {
                imagen.setImageResource(R.drawable.r_casa_1)
                PopupContent(
                    numero = "",
                    texto = "Si estás cocinando y se quema tu comida, trata de colocarle una tapa encima para parar el incendio.",
                    subTitulo = "Evíta que el humo se expanda"
                )
            }
            2 -> {
                imagen.setImageResource(R.drawable.r_casa_2)
                PopupContent(
                    numero = "",
                    texto = "Desconecta los cargadores que no uses. Podrían sobrecalentarse y provocar un incendio. ¡Desconéctalos siempre!",
                    subTitulo = ""
                )
            }
            3 -> {
                imagen.setImageResource(R.drawable.r_casa_3)
                PopupContent(
                    numero = "",
                    texto = "Ubica las salidas de emergencia de donde vives para evacuar eficientemente en caso de incendio.",
                    subTitulo = "Salidas de emergencia"
                )
            }
            4 -> {
                imagen.setImageResource(R.drawable.r_casa_4)
                PopupContent(
                    numero = "",
                    texto = "Dirígete a la zona de menor riesgo donde logres llamar al 123 en cuanto puedas.",
                    subTitulo = "Llamada a emergencias"
                )
            }
            5 -> {
                imagen.setImageResource(R.drawable.r_casa_5)
                PopupContent(
                    numero = "",
                    texto = "Tapate la boca y nariz con un paño húmedo para que no te afecte el humo.",
                    subTitulo = "Protégete del humo"
                )
            }
            else -> return
        }

        titulo.text = "Reacción en casa"
        numero.text = contenido.numero
        texto.text = contenido.texto
        subTitulo.text = contenido.subTitulo
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }
    //imagen.setImageResource(R.drawable.reaccion_casa_image_2)

    fun showPopupWindowPartesExtintor(view: View) {
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
            buttonFinalizar.visibility = View.VISIBLE
            buttonSiguiente.visibility = View.GONE
            buttonAtras.visibility = View.GONE
        }
        blurBackground.visibility = View.VISIBLE
        updatePopupWindowPartesExtintor()

        buttonClose.setOnClickListener {
            botonSecundario = 1
            popupWindow.dismiss()
        }
        buttonFinalizar.setOnClickListener {
            botonSecundario = 1
            popupWindow.dismiss()

        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    private fun updatePopupWindowPartesExtintor() {
        buttonAtras.visibility = if (botonSecundario > 1) View.VISIBLE else View.GONE

        val contenido = when (botonSecundario) {
            1 -> {
                imagen.setImageResource(R.drawable.p_extintor_1)
                PopupContent(
                    numero = "",
                    texto = "Conoce las partes del extintor para actuar rápida y correctamente en caso de emergencia.",
                    subTitulo = ""
                )
            }

            else -> return
        }

        titulo.text = "Partes del extintor"
        numero.text = contenido.numero
        texto.text = contenido.texto
        subTitulo.text = contenido.subTitulo
        // imagen.setImageResource(...) // Puedes asignar las imágenes aquí según el número
    }
}