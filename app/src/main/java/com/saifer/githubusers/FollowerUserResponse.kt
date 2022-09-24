package com.saifer.githubusers

import com.google.gson.annotations.SerializedName

data class FollowerUserResponse(

	@field:SerializedName("FollowerUserResponse")
	val followerUserResponse: List<FollowerUserResponseItem?>? = null
)

data class FollowerUserResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null
)
