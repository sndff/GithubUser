package com.saifer.githubusers.detail

import com.google.gson.annotations.SerializedName

data class FollowingUserResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null
)
