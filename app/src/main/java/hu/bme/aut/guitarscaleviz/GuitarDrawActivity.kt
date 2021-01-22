package hu.bme.aut.guitarscaleviz

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.aut.bme.guitarscaleviz.model.Scale
import hu.bme.aut.guitarscaleviz.model.Fretboard
import kotlinx.android.synthetic.main.activity_guitar_draw.*

class GuitarDrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val intent = intent
        val fret: Fretboard = intent.getSerializableExtra("Fretboard") as Fretboard
        val scale: Scale = intent.getSerializableExtra("Scale") as Scale
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guitar_draw)
        myview.setFretAndScale(fret, scale)

    }
}
