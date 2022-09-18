package com.saifer.githubusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_user)
        var tvName: TextView = itemView.findViewById(R.id.usr_name)
        var tvUsername: TextView = itemView.findViewById(R.id.usr_username)
        var tvFollower: TextView = itemView.findViewById(R.id.usr_follower)
        var tvFollowing: TextView = itemView.findViewById(R.id.usr_following)
        var tvRepository: TextView = itemView.findViewById(R.id.usr_repo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatar, name, username, follower, following, repository) = listUser[position]
        Glide.with(holder.itemView.context)
            .load(avatar)
            .circleCrop()
            .into(holder.imgAvatar)
        holder.tvName.text = name
        holder.tvUsername.text = username
        holder.tvFollower.text = follower
        holder.tvFollowing.text = following
        holder.tvRepository.text = repository

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }


    override fun getItemCount(): Int = listUser.size
}
