package com.levagency.miziki.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import com.levagency.miziki.database.dao.PersonDatabaseDao
import com.levagency.miziki.domain.album.entity.DatabaseAlbum
import com.levagency.miziki.domain.person.entity.DatabasePerson

private lateinit var INSTANCE: MizikiDatabase

fun getDatabase(context: Context): MizikiDatabase {
    synchronized(MizikiDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MizikiDatabase::class.java,
                "mizikidb"
            ).build()
        }
        return INSTANCE
    }
}
@Database(
    entities = [
        DatabaseAlbum::class,
        DatabasePerson::class
    ], version = 1
)
abstract class MizikiDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDatabaseDao
    abstract val personDao: PersonDatabaseDao
}