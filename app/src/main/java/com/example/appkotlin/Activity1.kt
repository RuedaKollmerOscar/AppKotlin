package com.example.appkotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout

class Activity1 : AppCompatActivity() {

    lateinit var rootLayout: ConstraintLayout
    lateinit var secondLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)
        rootLayout = this.findViewById(R.id.rootLayout)
        secondLayout = this.findViewById(R.id.secondLayout)
        main()
    }

    private fun main() {
        body(Color.BLACK, Color.RED)
    }

    private fun body(rootColor: Int, secondColor: Int) {
        // Establecer el color de fondo del "cuerpo"
        rootLayout.setBackgroundColor(rootColor)
        secondLayout.setBackgroundColor(secondColor)
    }
    override fun onPause() {
        super.onPause()
        Log.v("MainActivity", "Esta en pausa¡")
    }

    override fun onResume() {
        super.onResume()
        Log.v("MainActivity", "Reanuda la aplicacion")
    }
}