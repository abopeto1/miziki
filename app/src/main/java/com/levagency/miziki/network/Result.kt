package com.levagency.miziki.network

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

sealed class Result<out T> {
    data class Success<T>(val data: T?): Result<T>()

    data class Failure(val code: Int?): Result<Nothing>()

    object NetworkError: Result<Nothing>()
}

abstract class CallDelegate<TIn, TOut>(protected val proxy: Call<TIn>): Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()

    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)

    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()

    override fun request(): Request = proxy.request()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)

    abstract fun cloneImpl(): Call<TOut>
}

class ResultCall<T>(proxy: Call<T>): CallDelegate<T, Result<T>>(proxy){
    override fun enqueueImpl(callback: Callback<Result<T>>) {
        proxy.enqueue(object: Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                Timber.i(response.body().toString())
                
                val code = response.code()
                val result = if(code in 200 until 300) {
                    val body = response.body()
                    val successResult: Result<T> = Result.Success(body)
                    successResult
                } else {
                    Result.Failure(code)
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Timber.i(t)
                val result = if(t is IOException) {
                    Result.NetworkError
                } else {
                    Result.Failure(null)
                }

                callback.onResponse(this@ResultCall, Response.success(result))
            }

        })
    }

    override fun cloneImpl(): Call<Result<T>> = ResultCall(proxy.clone())

    /**
     * Returns a timeout that spans the entire call: resolving DNS, connecting, writing the request
     * body, server processing, and reading the response body. If the call requires redirects or
     * retries all must complete within one timeout period.
     */
    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}