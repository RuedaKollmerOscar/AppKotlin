package com.example.appkotlin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class Activity1 : AppCompatActivity(), OnClickListener {

    lateinit var rootLayout: ConstraintLayout
    lateinit var secondLayout: ConstraintLayout

    lateinit var btnCambiarFondo:Button
    lateinit var btnCambiarActivity:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)

        rootLayout = this.findViewById(R.id.rootLayout)
        secondLayout = this.findViewById(R.id.secondLayout)

        btnCambiarActivity = this.findViewById(R.id.cambiarActivity)
        btnCambiarFondo = this.findViewById(R.id.cambiarFondo)

        btnCambiarActivity.setOnClickListener(this)
        btnCambiarFondo.setOnClickListener(this)
        main()
    }

    private fun main() {
        body(Color.BLACK, Color.RED)
    }

    private fun body(rootColor: Int, secondColor: Int) {
        rootLayout.setBackgroundColor(rootColor)
        secondLayout.setBackgroundColor(secondColor)
    }

    override fun onClick(v: View?) {
        if (v!!.id==R.id.cambiarFondo){
            secondLayout.setBackgroundColor(Color.WHITE)
        } else if (v!!.id==R.id.cambiarActivity){
            var activity2:Intent = Intent(this, Activity2::class.java)
            startActivity(activity2)
        }
    }
}