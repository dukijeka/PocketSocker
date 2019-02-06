package rs.ac.bg.etf.rti.md150625d.dushan.pocketsocker.database

import android.arch.persistence.room.TypeConverter
import java.util.Date

class GameDatabaseConverters {


    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }


}
