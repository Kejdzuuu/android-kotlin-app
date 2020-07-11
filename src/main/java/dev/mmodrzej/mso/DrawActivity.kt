package dev.mmodrzej.mso

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.app.DialogCompat
import androidx.lifecycle.ViewModelProvider

class DrawActivity : AppCompatActivity() {

    private lateinit var drawView: DrawView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        drawView = findViewById(R.id.draw_view)
    }

    override fun onBackPressed() {
        val adb = AlertDialog.Builder(this)
        adb.setTitle("Back to main menu?")
        adb.setPositiveButton("Yes") { _, _ ->
            super.onBackPressed()
        }
        adb.setNegativeButton("No") { _, _ ->
        }
        adb.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_draw, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.green -> drawView.setColor(Color.GREEN)
            R.id.red -> drawView.setColor(Color.RED)
            else -> drawView.setColor(Color.BLUE)
        }

        return true
    }
}
