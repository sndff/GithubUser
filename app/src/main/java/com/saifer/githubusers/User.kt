package com.saifer.githubusers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar: String?,
    var username: String?,
    var id: Int,
    var name: String?,
    var followers: String?,
    var following: String?,
    var repo: String?,
    var location: String?,
    var company: String?
) : Parcelable
