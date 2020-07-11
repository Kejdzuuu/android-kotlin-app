package dev.mmodrzej.mso

//import android.support.v7.app.AppCompatActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NativeActivity : AppCompatActivity() {

    private lateinit var sortButton: Button
    private lateinit var inputField: EditText
    private lateinit var outputField: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native)

        sortButton = findViewById(R.id.sortButton)
        inputField = findViewById(R.id.native_input_field)
        outputField = findViewById(R.id.sorted_field)

        sortButton.setOnClickListener {
            it.hideKeyboard()
            sortInput()
        }
    }

    private fun sortInput() {
        val input = inputField.text.split(" ").toTypedArray().map{ it.toInt() }
        val sortedArray = input.toIntArray()
        sortArrayJNI(sortedArray, sortedArray.size)
        var answer = ""
        for(item in sortedArray){
            answer += "$item "
        }
        outputField.text = answer
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private external fun sortArrayJNI(array: IntArray, size: Int)

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
