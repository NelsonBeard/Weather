package com.geekbrains.weather

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.weather.MainActivity.PersonCopy.personCopy

private val person = MainActivity.Person("Vasia", 30)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            printText(person)
        }

        for (i in 1 .. 10){
            println(i)
        }
        for (i in 10 downTo 1){
            println(i)
        }

    }

    data class Person(val name: String, val age: Int)

    private fun printText(person: Person) {
        val textViewForDataClass = findViewById<TextView>(R.id.textViewForDataClass)
        textViewForDataClass.text = person.name + " " + person.age

        val textViewForObjectCopy = findViewById<TextView>(R.id.textViewForObjectCopy)
        textViewForObjectCopy.text = personCopy.name + " " + personCopy.age

    }

    object PersonCopy {
        val personCopy = person.copy(age = 45)
    }
}






