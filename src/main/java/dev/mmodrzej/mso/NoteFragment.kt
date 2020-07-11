package dev.mmodrzej.mso

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*
import java.util.zip.Inflater

private const val NOTE_ID = "note_id"

class NoteFragment : Fragment() {

    private lateinit var note: Note
    private lateinit var noteField: EditText
    private lateinit var addNoteButton: Button
    private val noteDetailViewModel: NoteDetailViewModel by lazy {
        ViewModelProvider(this).get(NoteDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note()
        val noteId: UUID = arguments?.getSerializable(NOTE_ID) as UUID
        noteDetailViewModel.loadNote(noteId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        noteField = view.findViewById(R.id.note_contents)
        addNoteButton = view.findViewById(R.id.add_note_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDetailViewModel.noteLiveData.observe(
            viewLifecycleOwner,
            Observer { note ->
                note?.let {
                    this.note = note
                    updateUI()
                }
            })
    }

    override fun onStart() {
        super.onStart()
        val noteTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                note.content = sequence.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        noteField.addTextChangedListener(noteTextWatcher)

        addNoteButton.setOnClickListener {
            noteDetailViewModel.saveNote(note)
            activity?.onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
        noteDetailViewModel.saveNote(note)
    }

    private fun updateUI() {
        noteField.setText(note.content)
    }

    companion object {

        fun newInstance(noteId: UUID): NoteFragment {
            val args = Bundle().apply { putSerializable(NOTE_ID, noteId) }
            return NoteFragment().apply { arguments = args }
        }
    }
}