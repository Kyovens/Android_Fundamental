package com.dicoding.submission_01_fundamental.data.retrofit

import com.dicoding.submission_01_fundamental.BuildConfig
import com.dicoding.submission_01_fundamental.data.response.DetailUserResponse
import com.dicoding.submission_01_fundamental.data.response.ItemsItem
import com.dicoding.submission_01_fundamental.data.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getAllUser(
        @Query("q") username: String
    ): SearchUserResponse

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowers(
        @Path("username") username: String
    ): List<ItemsItem>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): List<ItemsItem>
}