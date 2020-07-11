package dev.mmodrzej.mso

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast

private const val TAG = "BackgroundService"

class BackgroundService : Service() {

    private lateinit var looper: Looper
    private lateinit var incomingHandler: IncomingHandler
    private lateinit var messenger: Messenger

    inner class IncomingHandler : Handler {
        constructor() : super()
        constructor(looper: Looper) : super(looper)

        override fun handleMessage(msg: Message) {
            Log.d(TAG, "Message: ${msg.what}")
            val result = fibonacci(msg.what)
            val reply = Message.obtain(null, result)
            msg.replyTo.send(reply)
            super.handleMessage(msg)
        }
    }

    override fun onCreate() {
        Log.d(TAG, "Start thread")
        val thread = HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        looper = thread.looper
        incomingHandler = IncomingHandler(looper)
        messenger = Messenger(incomingHandler)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return messenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "Service unbound")
        Toast.makeText(this, "Service unbound", Toast.LENGTH_LONG).show()
        return super.onUnbind(intent)
    }

    private fun fibonacci(index: Int): Int {
        if(index <= 2) return 1
        return fibonacci(index-1) + fibonacci(index-2)
    }

}