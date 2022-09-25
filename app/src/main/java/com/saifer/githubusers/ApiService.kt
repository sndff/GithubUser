package com.saifer.githubusers

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(@Query("q") q : String): Call<FindUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<List<FollowerUserResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<List<FollowingUserResponseItem>>

}