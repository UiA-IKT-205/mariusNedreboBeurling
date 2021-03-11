package com.example.piano

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.core.net.toUri
import com.example.piano.data.Note
import com.example.piano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.*
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream
import java.net.URI

class PianoLayout : Fragment() {

    var onSave:((file:Uri) -> Unit)? = null

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2")
    private val halfTones = listOf("C#","D#","F#","G#","A#","C2#","D2#","F2#")
    private var score:MutableList<Note> = mutableListOf<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root
        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        fullTones.forEach {
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            var startPlay:Long = 0

            fullTonePianoKey.onKeyDown = { note ->
                startPlay = System.nanoTime()
                println("Piano key down $note")
            }
            fullTonePianoKey.onKeyUp ={
                var endPlay = System.nanoTime()
                val note = Note(it, startPlay,endPlay)
                score.add(note)
                println("Piano key up $note")
            }

            ft.add(view.pianoKeyslayout.id,fullTonePianoKey,"note_$it")

        }

        halfTones.forEach {
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(it)
            var startPlay:Long = 0
            halfTonePianoKey.onKeyDown = { note ->
                startPlay = System.nanoTime()
                println("Piano key down $note")
            }
            halfTonePianoKey.onKeyUp ={ note ->
                var endPlay = System.nanoTime()
                val note = Note(it,startPlay,endPlay)
                score.add(note)
                println("Piano key up $note")
            }

            ft.add(view.pianobKeyslayout.id,halfTonePianoKey,"note_$it")

        }

        ft.commit()

        view.saveScoreBt.setOnClickListener{

            var fileName =view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            val newFile = (File(path, fileName))

            when {
                score.count() == 0 -> Toast.makeText(activity, "Nothing to save", Toast.LENGTH_SHORT).show()
                fileName.isEmpty() -> Toast.makeText(activity, "No Name", Toast.LENGTH_SHORT).show()
                path == null -> Toast.makeText(activity, "No path", Toast.LENGTH_SHORT).show()
                newFile.exists() -> Toast.makeText(activity, "Already a file with that name", Toast.LENGTH_SHORT).show()

                else -> {
                    fileName = "$fileName.musikk"
                    val file = File(path,fileName)
                    FileOutputStream(file, true).bufferedWriter().use{ writer ->
                        score.forEach {
                            writer.write("${it.toString()}\n")
                    }

                    this.onSave?.invoke(file.toUri());
                    FileOutputStream(newFile).close()
                 }

                    score.clear()

                }
            }

        }




        return view
    }


}