package hu.bme.aut.guitarscaleviz.DAL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "currentselectedguitar")
data class CurrentSelectedGuitar (@PrimaryKey(autoGenerate = false) val id: Int,
                                  @ColumnInfo(name="current") val currentNumber: Int){}