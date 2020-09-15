package com.levagency.miziki

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.levagency.miziki.database.dao.AlbumDatabaseDao
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.models.Album
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class MizikiDatabaseTest {
    private lateinit var albumDatabaseDao: AlbumDatabaseDao
    private lateinit var db: MizikiDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, MizikiDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        albumDatabaseDao = db.albumDatabaseDao
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetAlbum() {
        val album = Album(1, name="Sublime 2")
        albumDatabaseDao.insert(album)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}