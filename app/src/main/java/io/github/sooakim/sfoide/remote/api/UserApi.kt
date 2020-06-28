package io.github.sooakim.sfoide.remote.api

import io.github.sooakim.sfoide.remote.resopnse.ListResponse
import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi{
    @GET("/api")
    fun getUsers(
            @Query("page")
            page: Int,
            @Query("results")
            results: Int,
            @Query("seed")
            seed: String,
            @Query("gender")
            gender: String? = null
    ): Single<ListResponse<UserResponse>>
}