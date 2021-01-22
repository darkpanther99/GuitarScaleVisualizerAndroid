package hu.bme.aut.guitarscaleviz

import android.content.Context
import android.util.Log
import androidx.room.Room
import hu.bme.aut.guitarscaleviz.DAL.GuitarDatabase
import hu.bme.aut.guitarscaleviz.DAL.GuitarSettings
import hu.bme.aut.guitarscaleviz.DAL.GuitarSettingsDAO
import kotlin.concurrent.thread

//Since this facade is singleton, I don't have to make the database singleton, because it will only get instantiated here, once
object SettingsFacade {
    lateinit var db : GuitarDatabase
    lateinit var settingsDAO : GuitarSettingsDAO
    var counter = 0 //TODO: store this persistently in a database table, so the counter will initialize to that upon starting the app
    var current = 1 //SQLite starts counting from 1

    fun buildDB(applicationContext: Context){
        //Build the db
            db = Room.databaseBuilder(
                applicationContext,
                GuitarDatabase::class.java, "guitardb"
            ).fallbackToDestructiveMigration().build()

            settingsDAO = db.GuitarSettingsDAO()
        thread{
            counter = settingsDAO.getAll().size
        }

    }

    fun getLast() : GuitarSettings{
        val setting =  settingsDAO.getSetting(current)
        return setting
    }

    fun getNext() : GuitarSettings{
        if (current != counter) {
            current++
        } else {
            current = 1
        }
        val setting =  settingsDAO.getSetting(current)

        return setting
    }

    fun insertSetting(setting: GuitarSettings){
        settingsDAO.insertSettings(setting)
        counter = settingsDAO.getAll().size
    }

}