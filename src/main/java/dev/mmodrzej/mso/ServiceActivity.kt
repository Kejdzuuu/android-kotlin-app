package dev.mmodrzej.mso

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.Exception

private const val TAG = "ServiceActivity"

class ServiceActivity : AppCompatActivity() {

    private lateinit var serviceButton: Button
    private lateinit var userInputText: EditText
    private lateinit var resultTextView: TextView

    private var messenger: Messenger? = null
    private var isBound = false

    private val replyMessenger = Messenger(ReplyHandler())

    inner class ReplyHandler : Handler() {

        override fun handleMessage(msg: Message) {
            Log.d(TAG, "Reply Message received: ${msg.what}")
            resultTextView.text = "Result: ${msg.what}"
            resultTextView.visibility = TextView.VISIBLE
            super.handleMessage(msg)
        }
    }

    private var serviceConnection = object: ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
            messenger = Messenger(service)
            Log.d(TAG, "Service connected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            messenger = null
            isBound = false
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        serviceButton = findViewById(R.id.start_service_button)
        userInputText = findViewById(R.id.input_edit_text)
        resultTextView = findViewById(R.id.result_text_view)

        userInputText.setOnClickListener {
            resultTextView.visibility = TextView.INVISIBLE
        }

        serviceButton.setOnClickListener {
            it.hideKeyboard()
            if(isBound) {
                var num = userInputText.text.toString().toIntOrNull() ?: 0
                var msg = Message.obtain(null, num)
                msg.replyTo = replyMessenger
                messenger?.send(msg) ?: Log.d(TAG, "Failed to send message")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent()
        intent.setClassName("dev.mmodrzej.mso", "dev.mmodrzej.mso.BackgroundService")
        val bound = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        unbindService(serviceConnection)
    }

    override fun onDestroy() {
        Log.d(TAG, "ServiceActivity destroyed")
        super.onDestroy()
    }
}