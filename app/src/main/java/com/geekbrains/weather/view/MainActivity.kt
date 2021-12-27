package com.geekbrains.weather.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.ActivityMainBinding
import com.geekbrains.weather.model.ConnectivityActionReceiver


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val receiver = ConnectivityActionReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        supportFragmentManager?.apply {
            beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .addToBackStack("")
                .commit()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}







