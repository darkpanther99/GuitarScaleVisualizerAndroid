package hu.bme.aut.guitarscaleviz.DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GuitarSettingsDAO {
    @Query("SELECT * FROM guitarsettings WHERE id = :ID LIMIT 1")
    fun getSetting(ID: Int) : GuitarSettings //SQLite Counts from 1, not from 0

    @Query("SELECT * FROM guitarsettings")
    fun getAll() : List<GuitarSettings>

    @Insert
    fun insertSettings(vararg settings: GuitarSettings)

    @Delete
    fun delete(setting: GuitarSettings)

}