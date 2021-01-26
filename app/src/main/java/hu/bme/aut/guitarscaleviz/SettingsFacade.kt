package hu.bme.aut.guitarscaleviz

import android.content.Context
import android.util.Log
import androidx.room.Room
import hu.bme.aut.guitarscaleviz.DAL.*
import java.lang.RuntimeException
import kotlin.concurrent.thread

//Since this facade is singleton, I don't have to make the database singleton, because it will only get instantiated here, once
object SettingsFacade {
    lateinit var db : GuitarDatabase
    lateinit var settingsDAO : GuitarSettingsDAO
    lateinit var currentDAO : CurrentSelectedGuitarDAO
    var entityCount = 0
    var current = 1 //SQLite starts counting from 1

    fun buildDB(applicationContext: Context){
        //Build the db
            db = Room.databaseBuilder(
                applicationContext,
                GuitarDatabase::class.java, "guitardb"
            ).fallbackToDestructiveMigration().build()

            settingsDAO = db.GuitarSettingsDAO()
            currentDAO = db.CurrentSelectedGuitarDAO()

        thread{
            entityCount = settingsDAO.getAll().size
            current = loadCurrent()
        }

    }

    fun getLast() : GuitarSettings{
        if (entityCount == 0) throw RuntimeException("Empty Database!") //TODO exception handling
        val setting =  settingsDAO.getSetting(current)
        return setting
    }

    fun getNext() : GuitarSettings{
        if (entityCount == 0) throw RuntimeException("Empty Database!") //TODO exception handling
        if (current != entityCount) {
            current++
            saveCurrent()

        } else {
            current = 1
            saveCurrent()
        }
        val setting =  settingsDAO.getSetting(current)


        return setting
    }

    fun insertSetting(setting: GuitarSettings){
        settingsDAO.insertSettings(setting)
        entityCount = settingsDAO.getAll().size
    }

    private fun saveCurrent(){
        currentDAO.upsert(CurrentSelectedGuitar(0, current))
    }

    private fun loadCurrent():Int{
        if (entityCount == 0){
            return 1
        }
        return currentDAO.getCurrentSelected().currentNumber
    }

}