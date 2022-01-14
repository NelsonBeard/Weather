package com.geekbrains.weather.view

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.geekbrains.weather.R

class ContactsActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        checkPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> getContacts()
                !ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS
                ) -> AlertDialog.Builder(this).setTitle("Дай доступ")
                    .setMessage("Так надо")
                    .setPositiveButton("Дать доступ") { _, _ -> checkPermission() }
                    .setNegativeButton("Не давать доступ") {dialog, _ -> dialog.dismiss()}
                    .create()
                    .show()
                else -> Toast.makeText(
                    this,
                    "Не разрешено",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun getContacts() {

        val contentResolver: ContentResolver = contentResolver
        val contactsList = findViewById<TextView>(R.id.contacts_listener).apply {
            text = ""
        }

        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursor?.let {
            val columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            if (columnIndex >= 0) {
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name = cursor.getString(columnIndex)
                        contactsList.text = "${contactsList.text} $name \n"
                    }
                }
            }
            cursor?.close()
        }
    }
}