package com.geekbrains.weather.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.contacts){
            startActivity(Intent(this, ContactsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}







