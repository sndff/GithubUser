package com.saifer.githubusers

import com.google.gson.annotations.SerializedName

data class FollowingUserResponse(

	@field:SerializedName("FollowingUserResponse")
	val followingUserResponse: List<FollowingUserResponseItem?>? = null
)

data class FollowingUserResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null
)
