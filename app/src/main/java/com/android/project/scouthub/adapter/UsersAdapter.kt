package com.android.project.scouthub.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.project.scouthub.R
import com.android.project.scouthub.databinding.ItemUserBinding
import com.android.project.scouthub.model.User
import com.bumptech.glide.Glide

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val user = differ.currentList[position]
                    val bundle = Bundle()
                    bundle.putSerializable("login", user.login)
                    findNavController(binding.root).navigate(R.id.action_homeFragment_to_repoFragment, bundle)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.binding.apply {
            Glide.with(root).load(user.avatar_url).into(userImageView)
            fullnameTextView.text = user.name
            userNameTextView.text = user.login
            bioTextView.text = user.bio
        }
    }
}
