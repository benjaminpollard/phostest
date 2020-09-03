package com.idea.group.phosgithubapp.repos

import com.idea.group.phosgithubapp.repos.models.RepoCallBack
import com.idea.group.phosgithubapp.services.BaseNetworkService
import com.idea.group.phosgithubapp.services.GitHubReposService
import com.idea.group.phosgithubapp.services.responceModels.GitRepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GithubUserRepo(
    private val serviceCon: BaseNetworkService
) {

    var repoCallback: RepoCallBack<List<GitRepoResponse>>? = null

    fun getAndroidUserRepoList() {
        val reposService: GitHubReposService =
            serviceCon.serviceConstructor(GitHubReposService::class.java) as GitHubReposService

        reposService.userAndroidRepos().enqueue(object : Callback<List<GitRepoResponse>> {
            override fun onFailure(call: Call<List<GitRepoResponse>>, t: Throwable) {
                repoCallback?.RepoResult(null, t.localizedMessage)
            }

            override fun onResponse(
                call: Call<List<GitRepoResponse>>,
                response: Response<List<GitRepoResponse>>
            ) {
                if (response.isSuccessful)
                    repoCallback?.RepoResult(response.body(), "")
                else
                    repoCallback?.RepoResult(null, response.message())
            }

        })
    }
}