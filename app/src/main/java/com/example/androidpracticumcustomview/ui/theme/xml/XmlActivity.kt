package com.example.androidpracticumcustomview.ui.theme.xml

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity


class XmlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startXmlPracticum()
    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        setContentView(customContainer)
        customContainer.setOnClickListener {
            finish()
        }

        val firstView = TextView(this).apply {
            text = "firstView"
            textSize = 24f
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.YELLOW)
        }

        val secondView = TextView(this).apply {
            text = "secondView"
            textSize = 24f
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.CYAN)
        }

        customContainer.addView(firstView)

        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)
    }
}