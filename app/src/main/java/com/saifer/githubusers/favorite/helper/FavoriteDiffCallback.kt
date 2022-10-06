package com.saifer.githubusers.favorite.helper

import androidx.recyclerview.widget.DiffUtil
import com.saifer.githubusers.favorite.database.Favorite

class FavoriteDiffCallback(private val mOldFavoriteList: List<Favorite>, private val mNewFavoriteList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = mOldFavoriteList[oldItemPosition]
        val new = mNewFavoriteList[newItemPosition]
        return old.avatar == new.avatar && old.login == new.login && old.id == new.id
    }

}