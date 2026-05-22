package com.example.redscate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class EditarPerfil : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_editar_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var opcionSeleccionada =""
        val nombreApellido = findViewById<EditText>(R.id.nombre_apellido)
        val edad = findViewById<EditText>(R.id.edad)
        val nombreApellidocontacto = findViewById<EditText>(R.id.nombre_apellido_emergencia)
        val telefono = findViewById<EditText>(R.id.telefono)
        val btnActualizar = findViewById<TextView>(R.id.btn_actualizar_perfil)
        //val rh = findViewById<AutoCompleteTextView>(R.id.rh)
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Lista de opciones de RH
        nombreApellido.setText(sharedPreferences.getString("nombre", "Valor por defecto"))
        edad.setText(sharedPreferences.getString("edad", ""))
        //rh.setText(sharedPreferences.getString("rh", ""))
        nombreApellidocontacto.setText(sharedPreferences.getString("nombreContacto", ""))
        telefono.setText(sharedPreferences.getString("telefono", ""))


        val spinnerRH: Spinner = findViewById(R.id.rh)

// Lista de opciones para el spinner
        val opcionesRH = listOf(
            "Seleccionar opción",
            "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
        )

// Adaptador para mostrar las opciones en el spinner
        val adapter = ArrayAdapter(
            this, // o requireContext() si estás en un Fragment
            android.R.layout.simple_spinner_item,
            opcionesRH
        )

// Estilo desplegable
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRH.adapter = adapter

// Manejar la selección de una opción
        spinnerRH.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                opcionSeleccionada = opcionesRH[position]
                if (position != 0) {
                    //Toast.makeText(this@MainActivity, "Seleccionaste: $opcionSeleccionada", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Opcional: manejar si no se selecciona nada
            }
        }

        val rhOptions = arrayOf("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-")

        // Adaptador para la lista
        //val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, rhOptions)

        // Configurar el adaptador en el AutoCompleteTextView
        //rh.setAdapter(adapter)
        //rh.threshold = 1


        btnActualizar.setOnClickListener {
            val nombre = nombreApellido.text.toString().trim()
            val age = edad.text.toString().trim()
            //val grupoSanguineo = rh.text.toString().trim()
            val contactName = nombreApellidocontacto.text.toString().trim()
            val contactNumber = telefono.text.toString().trim()// Validación de nombre
            if (nombre.isEmpty()) {
                Toast.makeText(this, "❌ Por favor escriba su nombre", Toast.LENGTH_SHORT).show()
                nombreApellido.setBackgroundResource(R.drawable.rounded_edit_text_rojo)
                return@setOnClickListener
            }

// Validación de RH
            if (opcionSeleccionada !in rhOptions) {
                Toast.makeText(this, "❌ RH inválido. Selecciona de la lista.", Toast.LENGTH_SHORT).show()
                spinnerRH.setBackgroundResource(R.drawable.rounded_edit_text_rojo)

                return@setOnClickListener
            }

// Validación de teléfono (opcional pero si se llena, debe tener 10 dígitos)
            if (contactNumber.isNotEmpty() && contactNumber.length != 10) {
                Toast.makeText(this, "❌ El teléfono no está completo", Toast.LENGTH_SHORT).show()
                telefono.setBackgroundResource(R.drawable.rounded_edit_text_rojo)
                return@setOnClickListener
            }

// Guardar datos
            editor.putString("nombre", nombre)
            editor.putString("edad", age)
            editor.putString("rh", opcionSeleccionada)
            editor.putString("nombreContacto", contactName)
            editor.putString("telefono", contactNumber)
            editor.apply()

// Mostrar GIF de éxito
            val blurBackground =findViewById<View>(R.id.blur_background)
            val gifExito = findViewById<ImageView>(R.id.gifExito)
            gifExito.visibility = View.VISIBLE
            blurBackground.visibility = View.VISIBLE

            Glide.with(this)
                .asGif()
                .load(R.drawable.perfil_editado)
                .into(gifExito)

// Ocultar el GIF después de 5 segundos y abrir perfil
            Handler(Looper.getMainLooper()).postDelayed({
                blurBackground.visibility = View.GONE
                gifExito.visibility = View.GONE
                startActivity(Intent(this, Perfil::class.java))
            }, 3250)

        }
        val botonAtras = findViewById<ImageView>(R.id.atras)
        botonAtras.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}