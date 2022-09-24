package com.saifer.githubusers

import retrofit2.Call
import retrofit2.http.*

//            Search : https://api.github.com/search/users?q={username}
//            Detail user : https://api.github.com/users/{username}
//            List Follower : https://api.github.com/users/{username}/followers
//            List Following : https://api.github.com/users/{username}/following

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") q : String): Call<FindUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ) : Call<FollowerUserResponse>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<FollowingUserResponse>

}