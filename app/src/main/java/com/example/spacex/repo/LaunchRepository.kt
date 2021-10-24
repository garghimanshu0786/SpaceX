package com.example.spacex.repo

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.example.spacex.LaunchDetailsQuery
import com.example.spacex.LaunchListQuery
import com.example.spacex.LaunchListQuery.Launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchRepository @Inject constructor(private val client: ApolloClient){
    private val TAG = this.javaClass.name

    suspend fun getLaunchList(dispatcher: CoroutineDispatcher): List<Launch>? =
        withContext(dispatcher) {
            try {
                val response = client.query(LaunchListQuery()).await()
                Log.d("LaunchList", "Success ${response.data}")
                response.data?.launches as List<Launch>?
            } catch (ex: Exception) {
                Log.e(TAG, "Error while fetch launch list $ex")
                emptyList()
            }
        }

    suspend fun getLaunchDetails(dispatcher: CoroutineDispatcher, id: String): LaunchDetailsQuery.Launch? =
        withContext(dispatcher) {
            try {
                val response = client.query(LaunchDetailsQuery(id)).await()
                Log.d("LaunchDetails", "Success ${response.data}")
                response.data?.launch
            } catch (ex: Exception) {
                Log.e(TAG, "Error while fetch launch details $ex")
                null
            }
        }
}