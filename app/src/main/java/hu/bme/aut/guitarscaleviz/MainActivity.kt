package hu.bme.aut.guitarscaleviz

import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import hu.bme.aut.guitarscaleviz.DAL.GuitarDatabase
import hu.bme.aut.guitarscaleviz.DAL.GuitarSettings
import hu.bme.aut.guitarscaleviz.model.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var mAdView : AdView
    private var instrument = "Guitar"
    private var stringNum = 6
    private var fretNum = 20

    private fun initAdView(){
        MobileAds.initialize(this) {}
        mAdView = findViewById(hu.bme.aut.guitarscaleviz.R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (resources.getBoolean(hu.bme.aut.guitarscaleviz.R.bool.isTablet)){
            requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onCreate(savedInstanceState)
        setContentView(hu.bme.aut.guitarscaleviz.R.layout.activity_main)

        initAdView()

        SettingsFacade.buildDB(applicationContext)

        val englishinstruments = mutableListOf<String>("Guitar", "Bass")
        val instruments = resources.getStringArray(hu.bme.aut.guitarscaleviz.R.array.instruments)
        val instrumentAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.simple_spinner_item, instruments)
        InstrumentSpinner.adapter = instrumentAdapter
        InstrumentSpinner.onItemSelectedListener = object : OnItemSelectedListener { //Anonymous class, which implements the OnItemSelectedListener interface
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                instrument = englishinstruments[InstrumentSpinner.selectedItemPosition]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        val stringNums = arrayOf(4, 5, 6, 7, 8, 9)
        val stringNumAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.simple_spinner_item, stringNums)
        StringNumSpinner.adapter = stringNumAdapter
        StringNumSpinner.setSelection(2)
        StringNumSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                stringNum = StringNumSpinner.selectedItem.toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }


        val fretNums = arrayOf(20, 21, 22, 23, 24)
        val fretNumAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.simple_spinner_item, fretNums)
        FretNumSpinner.adapter = fretNumAdapter
        FretNumSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fretNum = FretNumSpinner.selectedItem.toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }


        val flatOrSharp = resources.getStringArray(hu.bme.aut.guitarscaleviz.R.array.flatOrSharp)
        val flatOrSharpAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, R.layout.simple_spinner_item, flatOrSharp)
        FlatOrSharpSpinner.adapter = flatOrSharpAdapter
        FlatOrSharpSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (FlatOrSharpSpinner.selectedItemPosition == 1) {
                    Note.flatInstead(true)
                } else {
                    Note.flatInstead(false)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        //Database save-load buttons
        btnSave.setOnClickListener {
            thread{
                SettingsFacade.insertSetting(GuitarSettings(0, instrument, stringNum, fretNum))
            }
        }

        btnLoad.setOnClickListener {
            thread{
                val setting = SettingsFacade.getLast()
                runOnUiThread {
                    StringNumSpinner.setSelection(stringNums.indexOf(setting.stringNum), true)
                    FretNumSpinner.setSelection(fretNums.indexOf(setting.fretNum), true)
                    InstrumentSpinner.setSelection(englishinstruments.indexOf(setting.instrument), true)
                }

            }

        }

        scalefretbutton.setOnClickListener {
            val intent=Intent(this, ScaleFretboardActivity::class.java)
            intent.putExtra("Instrument", instrument)
            intent.putExtra("StringNum", stringNum)
            intent.putExtra("FretNum", fretNum)
            startActivity(intent)
        }

        btnBonus.setOnClickListener {
            //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")))
            //The original thing this button did was rickrolling people, now it has a better purpose. This line of code is here commented out, to commemorate that.
            thread{
                val setting = SettingsFacade.getNext()
                runOnUiThread {
                    StringNumSpinner.setSelection(stringNums.indexOf(setting.stringNum), true)
                    FretNumSpinner.setSelection(fretNums.indexOf(setting.fretNum), true)
                    InstrumentSpinner.setSelection(englishinstruments.indexOf(setting.instrument), true)
                }

            }
        }

    }



}