package com.idea.group.phosgithubapp.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idea.group.phosgithubapp.repos.DatabaseRepository
import com.idea.group.phosgithubapp.repos.GithubUserRepo
import com.idea.group.phosgithubapp.services.BaseNetworkService
import com.idea.group.phosgithubapp.viewModels.GitHubViewModel

class GitHubViewModelFactory(
    private val githubUserRepo: GithubUserRepo,
    private val baseNetworkService: BaseNetworkService,
    private val database: DatabaseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GitHubViewModel(githubUserRepo, baseNetworkService, database) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }

}