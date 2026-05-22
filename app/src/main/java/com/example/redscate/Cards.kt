package com.example.redscate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Cards : AppCompatActivity() {
    private lateinit var btnRecursos: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRecursos = findViewById(R.id.radar)
        btnRecursos.setOnClickListener {
            val intent = Intent(this, Radar::class.java)
            startActivity(intent)
        }


        //inicio de la logica del popUp
        val blur = findViewById<View>(R.id.blur_background)
        // ConstraintLayout que funciona como botón para mostrar el popup
        val reaccionCampo = findViewById<LinearLayout>(R.id.button_reaccion_campo)
        val claseReaccionCampo = Cards_1(this)
        val claseReaccionTrabajo = Cards_2(this)
        val claseReaccionkit = Cards_3(this)

        reaccionCampo.setOnClickListener {
            blur.visibility = View.VISIBLE
            claseReaccionCampo.showPopupWindowReaccionCampo(it)
        }
        val reaccionCiudad = findViewById<LinearLayout>(R.id.button_reaccion_ciudad)
        reaccionCiudad.setOnClickListener {
            claseReaccionCampo.showPopupWindowReaccionCiudad(it)
        }
        val reaccionCasa = findViewById<LinearLayout>(R.id.button_reaccion_casa)
        reaccionCasa.setOnClickListener {
            claseReaccionCampo.showPopupWindowReaccionCasa(it)
        }
        val reaccionTrabajo = findViewById<LinearLayout>(R.id.button_reaccion_trabajo)
        reaccionTrabajo.setOnClickListener {
            claseReaccionTrabajo.showPopupWindowReaccionTrabajo(it)
        }
        val kitEmergencia = findViewById<LinearLayout>(R.id.button_kit_emergencia)
        kitEmergencia.setOnClickListener {
            claseReaccionkit.showPopupWindoKitEmergencia(it)
        }
        val partesExtintor = findViewById<LinearLayout>(R.id.button_partes_extintor)
        partesExtintor.setOnClickListener {
            claseReaccionCampo.showPopupWindowPartesExtintor(it)
        }

        val tiposFuego = findViewById<LinearLayout>(R.id.button_tipos_fuego)
        tiposFuego.setOnClickListener {
            claseReaccionTrabajo.showPopupWindowTiposFuego(it)
        }

    }

    /*
        private fun showPopupWindowReaccionTrabajo(view: View) {
            // Inflar el layout del popup
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.activity_template_cards, null)

            val templateCards_1 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_1)
            templateCards_1.visibility = View.GONE
            val templateCards_2 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_2)
            templateCards_2.visibility = View.VISIBLE

            // Crear el PopupWindow
            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            // Configurar el fondo desenfocado
            val blurBackground = findViewById<View>(R.id.blur_background)
            blurBackground.visibility = View.VISIBLE

            // Configurar el PopupWindow para cerrarse al hacer clic fuera de él
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.setOnDismissListener {
                blurBackground.visibility = View.GONE // Ocultar el fondo desenfocado al cerrar el popup
            }

            // Declarar botonSecundario como var
            var botonSecundario = 1


            // Obtener referencias a las vistas dentro del popup
            val img_1 = popupView.findViewById<ImageView>(R.id.image_1)
            val subtitulo_1 = popupView.findViewById<TextView>(R.id.subtitulo_1)
            val t = popupView.findViewById<TextView>(R.id.texto_1)


            val subtitulo_2 = popupView.findViewById<TextView>(R.id.subtitulo_2)
            val texto_2 = popupView.findViewById<TextView>(R.id.texto_2)
            val img_2 = popupView.findViewById<ImageView>(R.id.image_2)

            val buttonAtras = popupView.findViewById<AppCompatButton>(R.id.button_atras)

            // Configurar el contenido inicial del popup
            botonSecundario = updatePopupContentReaccionTrabajo(
                botonSecundario, img_1, subtitulo_1, t, subtitulo_2, texto_2, img_2, buttonAtras
            )

            // Configurar el botón de cerrar
            val buttonClose = popupView.findViewById<ImageButton>(R.id.button_cerrar)
            buttonClose.setOnClickListener {
                popupWindow.dismiss()
            }

            // Configurar los botones "Atrás" y "Siguiente"
            val buttonSiguiente = popupView.findViewById<AppCompatButton>(R.id.button_siguiente)

            buttonAtras.setOnClickListener {

                botonSecundario--

                botonSecundario = updatePopupContentReaccionTrabajo(
                    botonSecundario, img_1, subtitulo_1, t, subtitulo_2, texto_2, img_2, buttonAtras
                )

            }

            buttonSiguiente.setOnClickListener {
                botonSecundario++ // Aumentar el número
                if (botonSecundario == 3) {
                    popupWindow.dismiss()
                }
                if (botonSecundario == 2) {
                    buttonSiguiente.setText("Finalizar")
                }
                botonSecundario = updatePopupContentReaccionTrabajo(
                    botonSecundario, img_1, subtitulo_1, t, subtitulo_2, texto_2, img_2, buttonAtras
                )
            }

            // Mostrar el popup en el centro de la pantalla
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        // Método para actualizar el contenido del popup
        private fun updatePopupContentReaccionTrabajo(
            botonSecundario: Int,
            img_1: ImageView,
            subtitulo_1: TextView,
            text1: TextView,
            subtitulo_2: TextView,
            texto_2: TextView,
            img_2: ImageView,
            buttonAtras: AppCompatButton
        ): Int { // Cambiar el tipo de retorno a Int
            when (botonSecundario) {
                1 -> {
                    buttonAtras.visibility = View.GONE

                    img_1.setImageResource(R.drawable.reaccion_casa_image_4)


                    subtitulo_1.text = "Humo en el aire"
                    text1.text =
                        "Si existe humo, arrastrate por el suelo tapando boca y nariz con un trapo humedo."
                    subtitulo_2.text = "No elevadores"
                    texto_2.text =
                        "No uses los elevadores en caso de emergencias que involucren salir rápidamente del edificio de trabajo."
                    img_2.setImageResource(R.drawable.reaccion_trabajo_image_2)


                }

                2 -> {

                    buttonAtras.visibility = View.VISIBLE

                    img_1.setImageResource(R.drawable.reaccion_trabajo_image_3)
                    subtitulo_1.text = "Cuidado con las quemaduras"
                    text1.text =
                        "Al momento de querer abrir una puerta, fíjate bien si estan calientes, ya que podría haber más fuego detrás de la puerta."
                    subtitulo_2.text = "Da voz de alerta."
                    texto_2.text =
                        "Si detectas humo en algún pasillo del edificio de trabajo, da la voz de alerta a todas las personas inmediatamente."
                    img_2.setImageResource(R.drawable.reaccion_trabajo_image_4)



                }
            }
            return botonSecundario
        }

        private fun showPopupWindoKitEmergencia(view: View) {
            // Inflar el layout del popup
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.activity_template_cards, null)


            // Crear el PopupWindow
            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            // Configurar el fondo desenfocado
            val blurBackground = findViewById<View>(R.id.blur_background)
            blurBackground.visibility = View.VISIBLE

            // Configurar el PopupWindow para cerrarse al hacer clic fuera de él
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.setOnDismissListener {
                blurBackground.visibility = View.GONE // Ocultar el fondo desenfocado al cerrar el popup
            }

            // Declarar botonSecundario como var
            var botonSecundario = 1

            // Obtener referencias a las vistas dentro del popup
            val subTitulo = popupView.findViewById<TextView>(R.id.subtitle)
            val numero = popupView.findViewById<TextView>(R.id.number_card)
            val texto = popupView.findViewById<TextView>(R.id.texts_cards)
            val imagen = popupView.findViewById<ImageView>(R.id.images_cards)
            val titulo = popupView.findViewById<TextView>(R.id.title_cards)
            setContrast(imagen, 1.2f)


            val tituloCards2 = popupView.findViewById<TextView>(R.id.title_cards_2)
            val text2 = popupView.findViewById<TextView>(R.id.text_2)
            val img_1 = popupView.findViewById<ImageView>(R.id.image_1)
            setContrast(img_1, 1.2f)

            val subtitulo_1 = popupView.findViewById<TextView>(R.id.subtitulo_1)
            val t = popupView.findViewById<TextView>(R.id.texto_1)


            val subtitulo_2 = popupView.findViewById<TextView>(R.id.subtitulo_2)
            val texto_2 = popupView.findViewById<TextView>(R.id.texto_2)
            val img_2 = popupView.findViewById<ImageView>(R.id.image_2)
            setContrast(img_2, 1.2f)


            val templateCards_1 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_1)
            val templateCards_2 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_2)


            val buttonAtras = popupView.findViewById<AppCompatButton>(R.id.button_atras)

            templateCards_1.visibility = View.VISIBLE
            templateCards_2.visibility = View.GONE

            // Configurar el contenido inicial del popup
            botonSecundario = updatePopupContentKitEmergencia(
                botonSecundario,
                titulo,
                subTitulo,
                numero,
                texto,
                imagen,
                img_1,
                subtitulo_1,
                t,
                subtitulo_2,
                texto_2,
                img_2,
                tituloCards2,
                text2,
                buttonAtras
            )

            // Configurar el botón de cerrar
            val buttonClose = popupView.findViewById<ImageButton>(R.id.button_cerrar)
            buttonClose.setOnClickListener {
                popupWindow.dismiss()
            }

            // Configurar los botones "Atrás" y "Siguiente"
            val buttonSiguiente = popupView.findViewById<AppCompatButton>(R.id.button_siguiente)


            buttonAtras.setOnClickListener {

                botonSecundario--

                botonSecundario = updatePopupContentKitEmergencia(
                    botonSecundario,
                    titulo,
                    subTitulo,
                    numero,
                    texto,
                    imagen,
                    img_1,
                    subtitulo_1,
                    t,
                    subtitulo_2,
                    texto_2,
                    img_2, tituloCards2,
                    text2,
                    buttonAtras
                )
                when (botonSecundario) {
                    1 -> {
                        templateCards_1.visibility = View.VISIBLE
                        templateCards_2.visibility = View.GONE
                    }

                    2 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                    }

                    3 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                    }

                    4 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE

                    }
                }
            }

            buttonSiguiente.setOnClickListener {
                botonSecundario++ // Aumentar el número

                botonSecundario = updatePopupContentKitEmergencia(
                    botonSecundario,
                    titulo,
                    subTitulo,
                    numero,
                    texto,
                    imagen,
                    img_1,
                    subtitulo_1,
                    t,
                    subtitulo_2,
                    texto_2,
                    img_2, tituloCards2,
                    text2,
                    buttonAtras
                )
                when (botonSecundario) {
                    1 -> {
                        templateCards_1.visibility = View.VISIBLE
                        templateCards_2.visibility = View.GONE
                    }

                    2 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                    }

                    3 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                    }

                    4 -> {
                        buttonAtras.visibility = View.VISIBLE
                        buttonSiguiente.setText("Finalizar")

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                    }
                    5 -> {
                        popupWindow.dismiss()
                    }
                }
            }


            // Mostrar el popup en el centro de la pantalla
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        // Método para actualizar el contenido del popup
        private fun updatePopupContentKitEmergencia(
            botonSecundario: Int,
            titulo: TextView,
            subTitulo: TextView,
            numero: TextView,
            texto: TextView,
            imagen: ImageView,
            img_1: ImageView,
            subtitulo_1: TextView,
            text1: TextView,
            subtitulo_2: TextView,
            texto_2: TextView,
            img_2: ImageView,
            tituloCards2: TextView,
            text2: TextView,
            buttonAtras: AppCompatButton
        ): Int { // Cambiar el tipo de retorno a Int
            when (botonSecundario) {
                1 -> {
                    buttonAtras.visibility = View.GONE

                    titulo.text = "Kit de emergencia"
                    subTitulo.text = ""
                    numero.text = ""
                    texto.text =
                        "Un kit es esencial para estar preparado en caso de desastres o situaciones inesperadas, aquí encontrarás una lista con las cosas más importantes para incluir en tu kit."
                    imagen.setImageResource(R.drawable.k_e_image_1)
                }

                2 -> {
                    buttonAtras.visibility = View.VISIBLE

                    tituloCards2.text = "Kit de emergencia"
                    text2.text =
                        "Encuentra una pequeña descripción de cada uno de los objetos que podrías incluir en tu kit."
                    img_1.setImageResource(R.drawable.k_e_image_2)
                    subtitulo_1.text = "Botiquín"
                    text1.text =
                        "Incluye elementos como vendas, curas, alcohol, botella de agua y gazas."
                    subtitulo_2.text = "Documentos"
                    texto_2.text =
                        "Guarda una copia de tus documentos en caso de una evacuación rápida."
                    img_2.setImageResource(R.drawable.k_e_image_3)


                }

                3 -> {
                    buttonAtras.visibility = View.VISIBLE

                    tituloCards2.text = "Kit de emergencia"
                    text2.text =
                        "Encuentra una pequeña descripción de cada uno de los objetos que podrías incluir en tu kit."
                    img_1.setImageResource(R.drawable.k_e_image_4)
                    subtitulo_1.text = "Linterna"
                    text1.text =
                        "Trae una contigo por si te desplazas por una zona de poca luz."
                    subtitulo_2.text = "Pilas / baterías"
                    texto_2.text = "Te ayudarán en caso de llevar linterna, radio u otros dispositivos."
                    img_2.setImageResource(R.drawable.k_e_image_5)
                }

                4 -> {
                    buttonAtras.visibility = View.VISIBLE

                    tituloCards2.text = "Kit de emergencia"
                    text2.text =
                        "Encuentra una pequeña descripción de cada uno de los objetos que podrías incluir en tu kit."
                    img_1.setImageResource(R.drawable.k_e_image_6)
                    subtitulo_1.text = "Papel higienico"
                    text1.text =
                        "Te servirá para uso de higiene personal en caso de emergencias que sean prolongadas.Incluye ele"
                    subtitulo_2.text = "Radio."
                    texto_2.text =
                        "Podrás escuchar alertas y noticias cuando las comunicaciones via internet fallen.."
                    img_2.setImageResource(R.drawable.k_e_image_7)

                }

            }


            return botonSecundario
        }

        private fun showPopupWindowPartesExtintor(view: View) {
            // Inflar el layout del popup
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.activity_template_cards, null)


            // Crear el PopupWindow
            val popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            // Configurar el fondo desenfocado
            val blurBackground = findViewById<View>(R.id.blur_background)
            blurBackground.visibility = View.VISIBLE

            // Configurar el PopupWindow para cerrarse al hacer clic fuera de él
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.setOnDismissListener {
                blurBackground.visibility = View.GONE // Ocultar el fondo desenfocado al cerrar el popup
            }

            // Declarar botonSecundario como var
            var botonSecundario = 1

            // Obtener referencias a las vistas dentro del popup
            val subTitulo = popupView.findViewById<TextView>(R.id.subtitle)
            val numero = popupView.findViewById<TextView>(R.id.number_card)
            val texto = popupView.findViewById<TextView>(R.id.texts_cards)
            val imagen = popupView.findViewById<ImageView>(R.id.images_cards)
            setContrast(imagen, 1.2f)
            val titulo = popupView.findViewById<TextView>(R.id.title_cards)

            val tituloCards2 = popupView.findViewById<TextView>(R.id.title_cards_2)
            val text2 = popupView.findViewById<TextView>(R.id.text_2)
            val img_1 = popupView.findViewById<ImageView>(R.id.image_1)
            setContrast(img_1, 1.2f)
            val subtitulo_1 = popupView.findViewById<TextView>(R.id.subtitulo_1)
            val t = popupView.findViewById<TextView>(R.id.texto_1)


            val subtitulo_2 = popupView.findViewById<TextView>(R.id.subtitulo_2)
            val texto_2 = popupView.findViewById<TextView>(R.id.texto_2)
            val img_2 = popupView.findViewById<ImageView>(R.id.image_2)
            setContrast(img_2, 1.2f)


            val templateCards_1 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_1)
            val templateCards_2 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_2)
            val templateCards_3 = popupView.findViewById<ConstraintLayout>(R.id.template_cards_3)

            val tituloCards3 = popupView.findViewById<TextView>(R.id.title_cards_3)
            val text2_1 = popupView.findViewById<TextView>(R.id.text_2_1)
            val img_1_1 = popupView.findViewById<ImageView>(R.id.image_1_1)
            setContrast(img_1_1, 1.2f)

            val subtitulo_1_1 = popupView.findViewById<TextView>(R.id.subtitulo_1_1)
            val t_1 = popupView.findViewById<TextView>(R.id.texto_1_1)

            val buttonAtras = popupView.findViewById<AppCompatButton>(R.id.button_atras)


            templateCards_1.visibility = View.GONE
            templateCards_2.visibility = View.VISIBLE
            templateCards_3.visibility = View.GONE

            // Configurar el contenido inicial del popup
            botonSecundario = updatePopupContentPartesExtintor(
                botonSecundario,
                titulo,
                subTitulo,
                numero,
                texto,
                imagen,
                img_1,
                subtitulo_1,
                t,
                subtitulo_2,
                texto_2,
                img_2,
                tituloCards2,
                text2,
                tituloCards3,
                text2_1,
                img_1_1,
                subtitulo_1_1,
                t_1,
                buttonAtras

            )

            // Configurar el botón de cerrar
            val buttonClose = popupView.findViewById<ImageButton>(R.id.button_cerrar)
            buttonClose.setOnClickListener {
                popupWindow.dismiss()
            }

            // Configurar los botones "Atrás" y "Siguiente"
            val buttonSiguiente = popupView.findViewById<AppCompatButton>(R.id.button_siguiente)


            buttonAtras.setOnClickListener {

                botonSecundario--

                botonSecundario = updatePopupContentPartesExtintor(
                    botonSecundario,
                    titulo,
                    subTitulo,
                    numero,
                    texto,
                    imagen,
                    img_1,
                    subtitulo_1,
                    t,
                    subtitulo_2,
                    texto_2,
                    img_2,
                    tituloCards2,
                    text2,
                    tituloCards3, text2_1, img_1_1, subtitulo_1_1, t_1, buttonAtras
                )
                when (botonSecundario) {
                    1 -> {
                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                        templateCards_3.visibility = View.GONE

                    }

                    2 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                        templateCards_3.visibility = View.GONE

                    }

                    3 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.GONE
                        templateCards_3.visibility = View.VISIBLE

                    }

                    4 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.VISIBLE
                        templateCards_2.visibility = View.GONE
                        templateCards_3.visibility = View.GONE

                    }
                }
            }

            buttonSiguiente.setOnClickListener {
                botonSecundario++ // Aumentar el número
                if (botonSecundario == 5) {
                    popupWindow.dismiss()

                }
                if (botonSecundario == 4) {
                    buttonSiguiente.setText("Finalizar")


                }
                botonSecundario = updatePopupContentPartesExtintor(
                    botonSecundario,
                    titulo,
                    subTitulo,
                    numero,
                    texto,
                    imagen,
                    img_1,
                    subtitulo_1,
                    t,
                    subtitulo_2,
                    texto_2,
                    img_2,
                    tituloCards2,
                    text2,
                    tituloCards3, text2_1, img_1_1, subtitulo_1_1, t_1, buttonAtras
                )
                when (botonSecundario) {
                    1 -> {
                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                        templateCards_3.visibility = View.GONE

                    }

                    2 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.VISIBLE
                        templateCards_3.visibility = View.GONE

                    }

                    3 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.GONE
                        templateCards_2.visibility = View.GONE
                        templateCards_3.visibility = View.VISIBLE

                    }

                    4 -> {
                        buttonAtras.visibility = View.VISIBLE

                        templateCards_1.visibility = View.VISIBLE
                        templateCards_2.visibility = View.GONE
                        templateCards_3.visibility = View.GONE

                    }
                }
            }


            // Mostrar el popup en el centro de la pantalla
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        // Método para actualizar el contenido del popup
        private fun updatePopupContentPartesExtintor(
            botonSecundario: Int,
            titulo: TextView,
            subTitulo: TextView,
            numero: TextView,
            texto: TextView,
            imagen: ImageView,
            img_1: ImageView,
            subtitulo_1: TextView,
            text1: TextView,
            subtitulo_2: TextView,
            texto_2: TextView,
            img_2: ImageView,
            tituloCards2: TextView,
            text2: TextView,
            tituloCards3: TextView,
            text2_1: TextView,
            img_1_1: ImageView,
            subtitulo_1_1: TextView,
            t_1: TextView,
            buttonAtras: AppCompatButton
        ): Int { // Cambiar el tipo de retorno a Int
            when (botonSecundario) {
                1 -> {
                    buttonAtras.visibility = View.GONE
                    tituloCards2.text = "Tipos de extintores"
                    text2.text =
                        "En este apartado encontrarás los diferentes tipos de extintores los cuales sirven para diferentes tipos de desastres naturales pasando por diferentes clases de incendio: A - B - C - D y K."
                    img_1.setImageResource(R.drawable.p_e_image_1)
                    subtitulo_1.text = "Clase A"
                    text1.text =
                        "Se genera en material combustible sólido como madera, cartón y plástico.\n" +
                                "\n" +
                                "Identificado con el color verde."
                    subtitulo_2.text = "Clase B"
                    texto_2.text =
                        "Se genera en material combustible liquido como aceite, gasolina o pintura.\n" +
                                "\n" +
                                "Identificado con el color rojo."
                    img_2.setImageResource(R.drawable.p_e_image_2)

                }

                2 -> {
                    buttonAtras.visibility = View.VISIBLE

                    tituloCards2.text = "Tipos de extintores"
                    text2.text =
                        "En este apartado encontrarás los diferentes tipos de extintores los cuales sirven para diferentes tipos de desastres naturales pasando por diferentes clases de incendio: A - B - C - D y K."

                    img_1.setImageResource(R.drawable.p_e_image_3)
                    subtitulo_1.text = "Clase C"
                    text1.text =
                        "Se genera en material combustible de gas como Brutano, Propano o Gas natural.\n" +
                                "\n" +
                                "Identificado con el color azul."
                    subtitulo_2.text = "Clase D"
                    texto_2.text =
                        "Se genera en material combustible de metal como Magnesio, Sodio o Aluminio.\n" +
                                "\n" +
                                "Identificado con el color amarillo."
                    img_2.setImageResource(R.drawable.p_e_image_4)


                }

                3 -> {
                    buttonAtras.visibility = View.VISIBLE

                    tituloCards3.text = "Tipos de extintores"
                    text2_1.text =
                        "En este apartado encontrarás los diferentes tipos de extintores los cuales sirven para diferentes tipos de desastres naturales pasando por diferentes clases de incendio: A - B - C - D y K."
                    img_1_1.setImageResource(R.drawable.p_e_image_5)
                    subtitulo_1_1.text = "Clase K"
                    t_1.text =
                        "Se genera en material derivado de aceites y grasas (Vegetales o animales).\n" +
                                "\n" +
                                "Identificado con el color negro."
                }

                4 -> {
                    buttonAtras.visibility = View.VISIBLE

                    titulo.text = "Partes del extintor"
                    subTitulo.text = ""
                    numero.text = ""
                    texto.text =
                        "Identifica las partes del extintor para en caso emergencia poder actuar de la forma más adecuada y rápida según lo requiera."
                    imagen.setImageResource(R.drawable.p_e_image_6)

                }
            }
            return botonSecundario
        }
        /*
                        private fun showPopupWindowReaccionCampo(view: View) {
                            // Inflar el layout del popup
                            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                            val popupView = inflater.inflate(R.layout.activity_template_cards, null)

                            // Crear el PopupWindow
                            val popupWindow = PopupWindow(
                                popupView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                true
                            )

                            // Configurar el fondo desenfocado
                            val blurBackground = findViewById<View>(R.id.blur_background)
                            blurBackground.visibility = View.VISIBLE

                            // Configurar el PopupWindow para cerrarse al hacer clic fuera de él
                            popupWindow.isOutsideTouchable = true
                            popupWindow.isFocusable = true
                            popupWindow.setOnDismissListener {
                                blurBackground.visibility = View.GONE // Ocultar el fondo desenfocado al cerrar el popup
                            }

                            // Declarar botonSecundario como var
                            var botonSecundario = 1

                            // Obtener referencias a las vistas dentro del popup
                            val subTitulo = popupView.findViewById<TextView>(R.id.subtitle)
                            val numero = popupView.findViewById<TextView>(R.id.number_card)
                            val texto = popupView.findViewById<TextView>(R.id.texts_cards)
                            val imagen = popupView.findViewById<ImageView>(R.id.images_cards)
                            val titulo = popupView.findViewById<TextView>(R.id.title_cards)


                            // Configurar el contenido inicial del popup
                            botonSecundario =
                                updatePopupContentReaccionCampo(
                                    botonSecundario,
                                    titulo,
                                    subTitulo,
                                    numero,
                                    texto,
                                    imagen
                                )

                            // Configurar el botón de cerrar
                            val buttonClose = popupView.findViewById<ImageButton>(R.id.button_cerrar)
                            buttonClose.setOnClickListener {
                                popupWindow.dismiss()
                            }

                            // Configurar los botones "Atrás" y "Siguiente"
                            val buttonAtras = popupView.findViewById<AppCompatButton>(R.id.button_atras)
                            val buttonSiguiente = popupView.findViewById<AppCompatButton>(R.id.button_siguiente)

                            buttonAtras.setOnClickListener {

                                botonSecundario--
                                if (botonSecundario == 0) {
                                    botonSecundario = 4
                                }
                                botonSecundario =
                                    updatePopupContentReaccionCampo(
                                        botonSecundario,
                                        titulo,
                                        subTitulo,
                                        numero,
                                        texto,
                                        imagen
                                    )

                            }

                            buttonSiguiente.setOnClickListener {
                                botonSecundario++ // Aumentar el número
                                if (botonSecundario == 5) {
                                    botonSecundario = 1
                                }
                                botonSecundario =
                                    updatePopupContentReaccionCampo(
                                        botonSecundario,
                                        titulo,
                                        subTitulo,
                                        numero,
                                        texto,
                                        imagen
                                    )
                            }

                            // Mostrar el popup en el centro de la pantalla
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                        }

                        // Método para actualizar el contenido del popup
                        private fun updatePopupContentReaccionCampo(
                            botonSecundario: Int,
                            titulo: TextView,
                            subTitulo: TextView,
                            numero: TextView,
                            texto: TextView,
                            imagen: ImageView
                        ): Int { // Cambiar el tipo de retorno a Int
                            when (botonSecundario) {
                                1 -> {
                                    titulo.text = "Reacción en campo"
                                    subTitulo.text = ""
                                    numero.text = "1."
                                    texto.text =
                                        "Evita las zonas con pendientes, los filos de montaña y valles estrechos, ya que el aire caliente tiende a ascender."
                                    imagen.setImageResource(R.drawable.reaccion_campo_image_1)
                                }

                                2 -> {
                                    titulo.text = "Reacción en campo"
                                    subTitulo.text = ""
                                    numero.text = "2."
                                    texto.text =
                                        "Evita refugiarte en pozos o cuevas, ya que el oxigeno se te podría acabar rápidamente."
                                    imagen.setImageResource(R.drawable.reaccion_campo_image_2)
                                }

                                3 -> {
                                    titulo.text = "Reacción en campo"
                                    subTitulo.text = "TENER EN CUENTA"
                                    numero.text = "3."
                                    texto.text =
                                        "No realices fogatas ni asados en temporada de verano ya que el pasto estará seco y subirá el riesgo de provocar un incendio forestal."
                                    imagen.setImageResource(R.drawable.reaccion_campo_image_3)
                                }

                                4 -> {
                                    titulo.text = "Reacción en campo"
                                    subTitulo.text = "TENER EN CUENTA"
                                    numero.text = "4."
                                    texto.text =
                                        "No arrojes las colillas de los cigarrillos en zonas verdes. Esto puede expandir aún más el incendio en caso de que ocurra."
                                    imagen.setImageResource(R.drawable.reaccion_campo_image_4)
                                }

                            }
                            return botonSecundario
                        }
                    */
    */
}
