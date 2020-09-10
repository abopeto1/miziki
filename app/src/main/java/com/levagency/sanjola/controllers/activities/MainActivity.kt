package com.levagency.sanjola.controllers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.levagency.sanjola.R

class MainActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var rollButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set references
        rollButton = findViewById(R.id.roll_button)
        image = findViewById(R.id.main_image)

        rollButton.setOnClickListener { rollDice() }
    }

    private fun rollDice() {
        val drawableResource = when ((1..6).random()) {
            1 -> R.drawable.ic_launcher_background
            2 -> R.drawable.ic_launcher_foreground
            3 -> R.drawable.ic_music
            4 -> R.drawable.ic_launcher_background
            5 -> R.drawable.ic_launcher_foreground
            else -> R.drawable.ic_music
        }

        image.setImageResource(drawableResource)
    }
}