package com.levagency.miziki.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levagency.miziki.database.dao.PersonDatabaseDao
import com.levagency.miziki.database.dao.RecentPlayedDao
import com.levagency.miziki.domain.album.dao.AlbumDatabaseDao
import com.levagency.miziki.domain.album.entity.DatabaseAlbum
import com.levagency.miziki.domain.artist.dao.ArtistDatabaseDao
import com.levagency.miziki.domain.artist.entity.DatabaseArtist
import com.levagency.miziki.domain.genre.entity.DatabaseGenre
import com.levagency.miziki.domain.genre.entity.GenreDatabaseDao
import com.levagency.miziki.domain.person.entity.DatabasePerson
import com.levagency.miziki.domain.playlist.dao.PlaylistDatabaseDao
import com.levagency.miziki.domain.playlist.entity.DatabasePlaylist
import com.levagency.miziki.domain.podcast.entity.DatabasePodcast
import com.levagency.miziki.domain.podcast.entity.PodcastDao
import com.levagency.miziki.domain.recent_played.entity.DatabaseRecentPlayed

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
        DatabaseArtist::class,
        DatabaseGenre::class,
        DatabasePerson::class,
        DatabasePlaylist::class,
        DatabasePodcast::class,
        DatabaseRecentPlayed::class,
    ], version = 1,
    exportSchema = false
)
abstract class MizikiDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDatabaseDao
    abstract val artistDao: ArtistDatabaseDao
    abstract val genreDao: GenreDatabaseDao
    abstract val personDao: PersonDatabaseDao
    abstract val playlistDao: PlaylistDatabaseDao
    abstract val podcastDao: PodcastDao
    abstract val recentPlayedDao: RecentPlayedDao
}