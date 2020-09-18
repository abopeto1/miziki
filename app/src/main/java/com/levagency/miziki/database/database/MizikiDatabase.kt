package com.levagency.miziki.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import com.levagency.miziki.album.entity.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class MizikiDatabase : RoomDatabase() {
    abstract val albumDatabaseDao: AlbumDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: MizikiDatabase? = null

        fun getInstance(context: Context): MizikiDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MizikiDatabase::class.java,
                        "sanjola_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}