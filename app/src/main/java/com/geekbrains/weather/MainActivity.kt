package com.geekbrains.weather

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val person = Person("Vasia", 30)
        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            printText(person)
        }

    }

    data class Person(val name: String, val age: Int)

    private fun printText(person: Person) {
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = person.name + " " + person.age
    }
}




