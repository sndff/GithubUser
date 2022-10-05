package com.saifer.githubusers.api

import com.saifer.githubusers.*
import com.saifer.githubusers.detail.DetailUserResponse
import com.saifer.githubusers.detail.FollowerUserResponseItem
import com.saifer.githubusers.detail.FollowingUserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getUser(@Query("q") q : String): Call<FindUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<List<FollowerUserResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<List<FollowingUserResponseItem>>

}