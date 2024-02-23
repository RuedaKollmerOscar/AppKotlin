package com.example.appkotlin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        // Obtener el mensaje enviado desde el Intent
        val mensaje = intent.getStringExtra("MENSAJE")

        // Mostrar el mensaje enviado en el TextView existente
        val bienvenidoTextView = findViewById<TextView>(R.id.bienvenidoTextView)
        bienvenidoTextView.text = mensaje
    }
}