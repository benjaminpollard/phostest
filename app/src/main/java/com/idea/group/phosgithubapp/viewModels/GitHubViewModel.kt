package com.idea.group.phosgithubapp.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idea.group.phosgithubapp.models.GitHubModel
import com.idea.group.phosgithubapp.repos.DatabaseRepository
import com.idea.group.phosgithubapp.repos.GithubUserRepo
import com.idea.group.phosgithubapp.repos.LastCommitRepo
import com.idea.group.phosgithubapp.repos.models.DatabaseUpdate
import com.idea.group.phosgithubapp.repos.models.RepoCallBack
import com.idea.group.phosgithubapp.services.BaseNetworkService
import com.idea.group.phosgithubapp.services.responceModels.GitRepoResponse
import com.idea.group.phosgithubapp.services.responceModels.LastCommitResponse

class GitHubViewModel(private val githubUserRepo: GithubUserRepo, private val baseService : BaseNetworkService, private val database:DatabaseRepository) : ViewModel() {

    private val callback: RepoCallBack<List<GitRepoResponse>> =
        object : RepoCallBack<List<GitRepoResponse>>() {
            override fun RepoResult(data: List<GitRepoResponse>?, error: String?) {
                showLoading.postValue(false)

                error?.let {
                    showError.postValue(it)
                }
                data?.let {
                    val items = responseToUiModels(it)
                    saveToDatabase(items)
                    gitHubItems.postValue(items)
                    loadReposDetails(items)
                }
            }
        }

    init {
        githubUserRepo.repoCallback = callback
    }

    val gitHubItems: MutableLiveData<List<GitHubModel>> = MutableLiveData<List<GitHubModel>>()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val showError: MutableLiveData<String> = MutableLiveData<String>()
    
    private val lastCommitRepos = mutableListOf<LastCommitRepo>()

    fun getItems() {
        showLoading.postValue(true)

        val items = database.getItems(GitHubModel::class.java)
        items?.let {
            showLoading.postValue(false)
            gitHubItems.postValue(it)
        }

        githubUserRepo.getAndroidUserRepoList()
    }

    fun removeListeners(owner: LifecycleOwner) {
        gitHubItems.removeObservers(owner)
        showError.removeObservers(owner)
        showLoading.removeObservers(owner)
    }

    private fun loadReposDetails(items: List<GitHubModel>) {
        
        for (item in items){
            val lastCommitRepo = LastCommitRepo(baseService)
            lastCommitRepo.getRepoLastCommitDetails(item.name)
            val callback = object : RepoCallBack<List<LastCommitResponse>>() {
                override fun RepoResult(data: List<LastCommitResponse>?, error: String?) {
                    database.update<GitHubModel>(object : DatabaseUpdate() {
                        override fun update() {
                            item.commitHash = data?.first()?.sha ?: ""
                            item.commitMessage = data?.first()?.commit?.message ?: ""
                        }

                    })
                }
            }
            lastCommitRepo.repoCallback = callback
            lastCommitRepos.add(lastCommitRepo)
        }
    }

    private fun responseToUiModels(items: List<GitRepoResponse>): List<GitHubModel> {
        val itemsToReturn = mutableListOf<GitHubModel>()
        for (item in items) {
            val model = GitHubModel()
            model.id = item.id
            model.name = item.name
            itemsToReturn.add(model)
        }
        return itemsToReturn
    }

    private fun saveToDatabase(items :List<GitHubModel>){
        database.remove(GitHubModel::class.java)
        database.saveItems(items)
    }
}