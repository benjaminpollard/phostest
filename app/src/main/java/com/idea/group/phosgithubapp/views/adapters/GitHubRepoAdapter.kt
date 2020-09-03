package com.idea.group.phosgithubapp.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idea.group.phosgithubapp.R
import com.idea.group.phosgithubapp.models.GitHubModel

class GitHubRepoAdapter(var items: List<GitHubModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GitHubRepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_git_hub_repo, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as GitHubRepoViewHolder
        if (items[position].commitHash != "")
            holder.lastCommitHash.text = items[position].commitHash
        else
            holder.lastCommitHash.text = holder.lastCommitHash.context.getString(R.string.loading)

        if (items[position].commitMessage != "")
            holder.lastCommitMessage.text = items[position].commitMessage
        else
            holder.lastCommitMessage.text =
                holder.lastCommitMessage.context.getString(R.string.loading)


        holder.repoName.text = items[position].name

    }

    internal class GitHubRepoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.row_item_git_hub_repo_name)
        val lastCommitMessage: TextView =
            itemView.findViewById(R.id.row_item_git_hub_repo_name_message)
        val lastCommitHash: TextView = itemView.findViewById(R.id.row_item_git_hub_repo_name_hash)
    }

}