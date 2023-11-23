package com.android.project.scouthub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.project.scouthub.databinding.ItemRepoBinding
import com.android.project.scouthub.model.RepoItem

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    inner class RepoViewHolder(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<RepoItem>() {
        override fun areItemsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.binding.apply {
            repoTitleTextView.text = user.name
            repoDescTextView.text = user.description

            if (user.topics?.isNotEmpty() == true) {
                repoTagsContainer.visibility = View.VISIBLE
                repoTagsContainer.removeAllViews()
                // Add a TextView for each tag
                for (tag in user.topics) {
                    val tagTextView = TextView(root.context)
                    tagTextView.text = tag
                    val layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(4, 0, 4, 0)
                    tagTextView.layoutParams = layoutParams
                    repoTagsContainer.addView(tagTextView)
                }
            } else {
                repoTagsContainer.visibility = View.GONE
            }

        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}