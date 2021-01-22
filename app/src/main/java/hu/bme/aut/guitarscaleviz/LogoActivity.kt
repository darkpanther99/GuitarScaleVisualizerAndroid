package hu.bme.aut.guitarscaleviz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import hu.bme.aut.guitarscaleviz.DAL.GuitarDatabase

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        startActivity(Intent(this, MainActivity::class.java))
    }
}