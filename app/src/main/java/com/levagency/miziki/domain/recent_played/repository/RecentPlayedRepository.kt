package com.levagency.miziki.domain.recent_played.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.levagency.miziki.database.database.MizikiDatabase
import com.levagency.miziki.domain.album.entity.asDatabaseModel
import com.levagency.miziki.domain.recent_played.entity.RecentPlayed
import com.levagency.miziki.domain.recent_played.entity.asDatabaseModel
import com.levagency.miziki.domain.recent_played.entity.asDomainModel
import com.levagency.miziki.network.MizikiApi
import com.levagency.miziki.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RecentPlayedRepository(val database: MizikiDatabase) {
    val recentPlayed: LiveData<List<RecentPlayed>> = Transformations.map(database.recentPlayedDao.getRecentPlayed()){
        it.asDomainModel()
    }

    suspend fun refreshAlbums(){
        withContext(Dispatchers.IO){
            when(val result = MizikiApi.recentPlayedApiService.getRecentPlayed()){
                is Result.Success -> {
                    result.data?.recentPlayed?.asDatabaseModel()?.let { database.recentPlayedDao.insertAll(it) }
                }
                is Result.Failure -> {
                    Timber.i(result.toString())
                    Timber.i(result.code.toString())
                }
                else -> Timber.i(result.toString())
            }
        }
    }
}