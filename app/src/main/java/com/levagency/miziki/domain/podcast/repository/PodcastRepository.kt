package com.levagency.miziki.domain.podcast.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.genre.entity.asDatabaseModel
import com.levagency.miziki.domain.podcast.entity.Podcast
import com.levagency.miziki.domain.podcast.entity.asDatabaseModel
import com.levagency.miziki.domain.podcast.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class PodcastRepository(
    private val database: MizikiDatabase
) {
    val podcasts: LiveData<List<Podcast>> = Transformations.map(database.podcastDao.getAll()){
        it.asDomainModel()
    }

    suspend fun refreshPodcasts(){
        withContext(Dispatchers.IO){
            when(val result = MizikiApi.podcastApiService.getPodcasts()) {
                is Result.Success -> {
                    result.data?.data?.asDatabaseModel()?.let { database.podcastDao.insertAll(it) }
                }
                is Result.Failure -> {
                    Timber.i(result.toString())
                }
                else -> Timber.i(result.toString())
            }
        }
    }
}