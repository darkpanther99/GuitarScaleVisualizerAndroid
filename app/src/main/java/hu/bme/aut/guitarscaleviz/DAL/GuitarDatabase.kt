package hu.bme.aut.guitarscaleviz.DAL

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(GuitarSettings::class, CurrentSelectedGuitar::class), version = 5)
abstract class GuitarDatabase:RoomDatabase() {
    abstract fun GuitarSettingsDAO() : GuitarSettingsDAO
    abstract fun CurrentSelectedGuitarDAO() : CurrentSelectedGuitarDAO
}