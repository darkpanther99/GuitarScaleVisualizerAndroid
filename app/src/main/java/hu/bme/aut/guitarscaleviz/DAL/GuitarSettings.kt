package hu.bme.aut.guitarscaleviz.DAL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guitarsettings")
data class GuitarSettings (@PrimaryKey(autoGenerate = true) val id: Int = 0,
                           @ColumnInfo(name="instrument") val instrument: String,
                           @ColumnInfo(name="stringnum") val stringNum: Int,
                           @ColumnInfo(name="fretnum") val fretNum: Int
){
}