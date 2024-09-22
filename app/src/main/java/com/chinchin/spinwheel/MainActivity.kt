package com.chinchin.spinwheel

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var rotatingCircleView: RotatingCircleView
    private lateinit var btnPlay: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        btnPlay = findViewById(R.id.btn_spin)
        rotatingCircleView = findViewById(R.id.rotatingCircleView)
        val items = listOf(
            ItemWheel("Item 1", Color.WHITE, Color.RED),
            ItemWheel("Item 2", Color.WHITE, Color.BLUE),
            ItemWheel("Item 3", Color.BLACK, Color.GREEN),
            ItemWheel("Item 4", Color.BLACK, Color.YELLOW),
            ItemWheel("Item 5", Color.BLACK, Color.CYAN),
            ItemWheel("Item 6", Color.WHITE, Color.MAGENTA),
            ItemWheel("Item 7", Color.WHITE, Color.LTGRAY),
            ItemWheel("Item 8", Color.BLACK, Color.DKGRAY),
            ItemWheel("Item 9", Color.WHITE, Color.BLUE),

        )
        rotatingCircleView.setItems(items)
        btnPlay.setOnClickListener {
            rotatingCircleView.startRotation()
        }

    }
}