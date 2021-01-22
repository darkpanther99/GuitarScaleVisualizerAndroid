package hu.bme.aut.guitarscaleviz

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import hu.aut.bme.guitarscaleviz.model.Scale
import hu.bme.aut.guitarscaleviz.model.Fretboard
import hu.bme.aut.guitarscaleviz.model.GuitarString
import hu.bme.aut.guitarscaleviz.model.Note
import hu.bme.aut.guitarscaleviz.model.NoteException
import kotlinx.android.synthetic.main.activity_scale_fretboard.*

class ScaleFretboardActivity : AppCompatActivity(), GuitarFragment.ScaleFretboardChanger {
    private var instrument = "Guitar"
    private var stringNum = 6
    private var fretNum = 20
    private var tuning = "Standard"
    private var tuningRoot = ""
    private var scaleMode = "Major"
    private var scaleRoot = ""
    private var isTablet=false;

    override fun onCreate(savedInstanceState: Bundle?) {
        isTablet = resources.getBoolean(R.bool.isTablet)
        if (isTablet){
            requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_fretboard)

        val intent= intent
        instrument = intent.getStringExtra("Instrument")
        stringNum = intent.getIntExtra("StringNum", 6)
        fretNum = intent.getIntExtra("FretNum", 20)


        val tunings = arrayOf("Standard", "Drop", "Custom")
        val tuningAdapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_spinner_item, tunings)
        TuningSpinner.adapter = tuningAdapter
        TuningSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (TuningSpinner.selectedItem == "Custom") {
                    customTuningInput.visibility = View.VISIBLE
                }
                else {
                    customTuningInput.visibility = View.INVISIBLE
                    tuning = TuningSpinner.selectedItem.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        val englishmodes= mutableListOf<String>("Major", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Minor", "Locrian", "Custom")
        val modes = resources.getStringArray(R.array.modes)
        val scaleAdapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_spinner_item, modes)
        ScaleSpinner.adapter = scaleAdapter
        ScaleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (englishmodes[ScaleSpinner.selectedItemPosition] == "Custom") {
                    customScaleInput.visibility = View.VISIBLE
                }
                else {
                    customScaleInput.visibility = View.INVISIBLE
                    scaleMode = englishmodes[ScaleSpinner.selectedItemPosition]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }


        TuningRootInput.setText("E")
        ScaleRootInput.setText("C")


        DoneButton.setOnClickListener { v ->
            tuningRoot = TuningRootInput.text.toString()
            scaleRoot = ScaleRootInput.text.toString()
            var fret: Fretboard? = null
            var s: Scale? = null
            try {
                if (TuningSpinner.selectedItem == "Custom") {
                    val StringNames = customTuningInput.text.toString()
                    val StringNamesSplit = StringNames.split("\\s".toRegex()).toTypedArray()
                    val guitarStrings: Array<GuitarString?> = arrayOfNulls<GuitarString>(StringNamesSplit.size)
                    for (i in StringNamesSplit.indices) {
                        guitarStrings[i] = GuitarString(StringNamesSplit[i], fretNum)
                    }
                    fret = Fretboard(guitarStrings, fretNum)
                } else {
                    fret = Fretboard(Note(tuningRoot), tuning, stringNum, instrument, fretNum)
                }
                if (ScaleSpinner.selectedItem == "Custom") {
                    val ScaleNotes = customScaleInput.text.toString()
                    val ScaleNotesSplit = ScaleNotes.split("\\s".toRegex()).toTypedArray()
                    val notes: Array<Note?> = arrayOfNulls<Note>(ScaleNotesSplit.size)
                    for (i in ScaleNotesSplit.indices) {
                        notes[i] = Note(ScaleNotesSplit[i])
                    }
                    s = Scale(notes)
                } else {
                    s = Scale(Note(scaleRoot), scaleMode)
                }

                if(isTablet==false) {
                    if (ScaleSpinner.selectedItem == "Custom") {
                        openGuitarIntent(fret, s)
                    } else {
                        openGuitarIntent(fret, s)
                    }
                }
                else{
                    onScaleFretboardChanged(s, fret)
                }

            } catch (e: NoteException) {
                toastErrorMsg(e)
            }
        }
    }


    private fun openGuitarIntent(
        fret: Fretboard?,
        scale: Scale?
    ) {
        val intent = Intent(this, GuitarDrawActivity::class.java)
        intent.putExtra("Fretboard", fret)
        intent.putExtra("Scale", scale)
        startActivity(intent)
    }

    private fun toastErrorMsg(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }


    override fun onScaleFretboardChanged(scale: Scale, fret: Fretboard) {
        val fragment = GuitarFragment().apply {
            arguments = Bundle().apply {
                putSerializable("Fretboard", fret)
                putSerializable("Scale", scale)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_detail_container, fragment)
            .commit()
    }
}
