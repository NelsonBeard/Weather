package com.geekbrains.weather.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectivityActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Подключение к сети изменилось", Toast.LENGTH_SHORT).show()
    }
}