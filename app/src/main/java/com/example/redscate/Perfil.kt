package com.example.redscate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Perfil : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        // Ajuste de márgenes según el sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar preferencias
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Referencias de vista
        val nombreApellido = findViewById<TextView>(R.id.nombre_apellido)
        val edad = findViewById<TextView>(R.id.edad)
        val rh = findViewById<TextView>(R.id.rh)
        val nombreContacto = findViewById<TextView>(R.id.nombre_apellido_emergencia)
        val telefono = findViewById<TextView>(R.id.telefono)

        // Cargar datos almacenados
        nombreApellido.text = sharedPreferences.getString("nombre", "Sin nombre")
        edad.text = sharedPreferences.getString("edad", "Edad no registrada")
        rh.text = sharedPreferences.getString("rh", "RH no registrado")
        nombreContacto.text = sharedPreferences.getString("nombreContacto", "Contacto no registrado")
        telefono.text = sharedPreferences.getString("telefono", "Teléfono no registrado")

        val rol = sharedPreferences.getString("perfil", "0") // Valor por defecto "0"

        // Botón para editar perfil
        findViewById<TextView>(R.id.btn_editar_perfil).setOnClickListener {
            startActivity(Intent(this, EditarPerfil::class.java))
        }

            // Botón atrás
            findViewById<ImageView>(R.id.atras).setOnClickListener {
                when (rol) {
                    "1" -> {
                        startActivity(Intent(this, CodigoRescatista::class.java))

                    }

                    "0" -> {
                        startActivity(Intent(this, Cards::class.java))

                    }

                    else -> {
                        Toast.makeText(this, "⚠️ Rol desconocido", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Botón para cambiar perfil
            findViewById<TextView>(R.id.btn_cambiar_perfil).setOnClickListener {
                when (rol) {
                    "0" -> {
                        editor.putString("perfil", "1").apply()
                        startActivity(Intent(this, CodigoRescatista::class.java))
                        Toast.makeText(this, "Cambiaste a Rescatista", Toast.LENGTH_SHORT).show()

                    }

                    "1" -> {
                        editor.putString("perfil", "0").apply()
                        startActivity(Intent(this, Cards::class.java))
                        Toast.makeText(this, "Cambiaste a Sobreviviente", Toast.LENGTH_SHORT).show()

                    }

                    else -> {
                        Toast.makeText(this, "⚠️ Rol desconocido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

