package dev.mmodrzej.mso

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var drawButton: Button
    private lateinit var databaseButton: Button
    private lateinit var serviceButton: Button
    private lateinit var networkButton: Button
    private lateinit var nativeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawButton = findViewById(R.id.draw_activity_button)
        databaseButton = findViewById(R.id.database_activity_button)
        serviceButton = findViewById(R.id.service_activity_button)
        networkButton = findViewById(R.id.network_activity_button)
        nativeButton = findViewById(R.id.native_activity_button)

        drawButton.setOnClickListener {
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
        }

        databaseButton.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        }

        serviceButton.setOnClickListener {
            val intent = Intent(this, ServiceActivity::class.java)
            startActivity(intent)
        }

        networkButton.setOnClickListener {
            Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show()
        }

        nativeButton.setOnClickListener {
            val intent = Intent(this, NativeActivity::class.java)
            startActivity(intent)
        }
    }
}