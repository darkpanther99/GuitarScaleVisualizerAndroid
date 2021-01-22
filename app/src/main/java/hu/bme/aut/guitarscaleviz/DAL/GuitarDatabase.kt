package hu.bme.aut.guitarscaleviz.DAL

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GuitarSettings::class), version = 4)
abstract class GuitarDatabase:RoomDatabase() {
    abstract fun GuitarSettingsDAO() : GuitarSettingsDAO
}