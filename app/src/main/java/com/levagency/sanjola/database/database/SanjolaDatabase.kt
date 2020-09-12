package com.levagency.sanjola.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levagency.sanjola.database.dao.AlbumDatabaseDao
import com.levagency.sanjola.models.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class SanjolaDatabase : RoomDatabase() {
    abstract val albumDatabaseDao: AlbumDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SanjolaDatabase? = null

        fun getInstance(context: Context): SanjolaDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SanjolaDatabase::class.java,
                        "sanjola_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}