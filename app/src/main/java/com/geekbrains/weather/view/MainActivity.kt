package com.geekbrains.weather.view

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
    }

}






