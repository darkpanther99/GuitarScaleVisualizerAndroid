package hu.bme.aut.guitarscaleviz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.aut.bme.guitarscaleviz.model.Scale
import hu.bme.aut.guitarscaleviz.model.Fretboard
import kotlinx.android.synthetic.main.fragment_guitar.*
import kotlinx.android.synthetic.main.fragment_guitar.view.*


class GuitarFragment : Fragment() {
    private lateinit var scale : Scale
    private lateinit var fretboard: Fretboard

    interface ScaleFretboardChanger {
        fun onScaleFretboardChanged(s : Scale, fret: Fretboard)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
                    scale = it.getSerializable("Scale") as Scale
                    fretboard = it.getSerializable("Fretboard") as Fretboard
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout= inflater.inflate(R.layout.fragment_guitar, container, false)
        layout.myview.setFretAndScale(fretboard, scale)
        return layout
    }

}