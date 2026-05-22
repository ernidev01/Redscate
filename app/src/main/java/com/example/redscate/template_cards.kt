package com.example.redscate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class template_cards : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_template_cards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

        /*var botonSecundario = 1
       // val buttonAtras = findViewById<AppCompatButton>(R.id.button_atras)
        //val buttonSiguiente = findViewById<AppCompatButton>(R.id.button_siguiente)

        // Configurar el botón "Atrás" para decrementar el número
        buttonAtras.setOnClickListener {
            botonSecundario-- // Disminuir el número
        }

        // Configurar el botón "Siguiente" para incrementar el número
        buttonSiguiente.setOnClickListener {
            botonSecundario++ // Aumentar el número
        }
        val subTitulo = findViewById<TextView>(R.id.subtitle)
        val numero = findViewById<TextView>(R.id.number_card)
        val texto = findViewById<TextView>(R.id.texts_cards)
        val imagen = findViewById<ImageView>(R.id.images_cards)
/*
        when (botonSecundario) {
            1 -> {
                subTitulo.text = ""
                numero.text = "1."
                texto.text =
                    "Evita las zonas con pendientes, los filos de montaña y valles estrechos, ya que el aire caliente tiende a ascender."
                imagen.setImageResource(R.drawable.reaccion_campo_image_1)

            }

            2 -> {
                subTitulo.text = ""
                numero.text = "2."
                texto.text =
                    "Evita refugiarte en pozos o cuevas, ya que el oxigeno se te podría acabar rápidamente."
                imagen.setImageResource(R.drawable.reaccion_campo_image_2)
            }

            3 -> {
                subTitulo.text = "TENER EN CUENTA"
                numero.text = "3."
                texto.text =
                    "No realices fogatas ni asados en temporada de verano ya que el pasto estará seco y subirá el riesgo de provocar un incendio forestal."
                imagen.setImageResource(R.drawable.reaccion_campo_image_3)
            }

            4 -> {
                subTitulo.text = "TENER EN CUENTA"
                numero.text = "4."
                texto.text =
                    "No arrojes las colillas de los cigarrillos en zonas verdes. Esto puede expandir aún más el incendio en caso de que ocurra."
                imagen.setImageResource(R.drawable.reaccion_campo_image_4)
            }

            else -> {
                // Reiniciar `botonSecundario` a 1 si su valor es mayor que 4
                botonSecundario = 1
            }
        }*/