package com.levagency.miziki.domain.podcast.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.podcast.entity.Podcast
import com.levagency.miziki.domain.podcast.entity.asDatabaseModel
import com.levagency.miziki.domain.podcast.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class PodcastRepository(
    private val database: MizikiDatabase
) {
    val podcasts: LiveData<List<Podcast>> = Transformations.map(database.podcastDao.getAll()){
        it.asDomainModel()
    }

    suspend fun refreshPodcasts(){
        withContext(Dispatchers.IO){
            try{
                val networkPodcast = MizikiApi.podcastApiService.getPodcasts()

                database.podcastDao.insertAll(networkPodcast.asDatabaseModel())
            } catch (e: HttpException) {
                Timber.i(e)
            }
        }
    }
}