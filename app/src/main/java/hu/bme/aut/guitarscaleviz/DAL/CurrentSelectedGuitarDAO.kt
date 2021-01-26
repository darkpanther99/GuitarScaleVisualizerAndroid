package hu.bme.aut.guitarscaleviz.DAL

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentSelectedGuitarDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(current: CurrentSelectedGuitar)

    @Query("SELECT * FROM currentselectedguitar WHERE id = 0")
    fun getCurrentSelected():CurrentSelectedGuitar

}