package com.saifer.githubusers.detail

import com.google.gson.annotations.SerializedName

data class FollowerUserResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("id")
	val id: Int
)
