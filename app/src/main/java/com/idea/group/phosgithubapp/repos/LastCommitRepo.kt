package com.idea.group.phosgithubapp.repos

import com.idea.group.phosgithubapp.repos.models.RepoCallBack
import com.idea.group.phosgithubapp.services.BaseNetworkService
import com.idea.group.phosgithubapp.services.GitHubLastCommitService
import com.idea.group.phosgithubapp.services.responceModels.LastCommitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LastCommitRepo(
    private val serviceCon: BaseNetworkService
) {

    var repoCallback: RepoCallBack<List<LastCommitResponse>>? = null

    fun getRepoLastCommitDetails(repoName: String) {
        val service: GitHubLastCommitService =
            serviceCon.serviceConstructor(GitHubLastCommitService::class.java) as GitHubLastCommitService
        val url = UrlConstants.CommitPreFix + repoName + UrlConstants.CommitPostFix

        service.userAndroidRepos(url).enqueue(object : Callback<List<LastCommitResponse>> {
            override fun onFailure(call: Call<List<LastCommitResponse>>, t: Throwable) {
                repoCallback?.RepoResult(null, t.localizedMessage)
            }

            override fun onResponse(
                call: Call<List<LastCommitResponse>>,
                response: Response<List<LastCommitResponse>>
            ) {
                if (response.isSuccessful)
                    repoCallback?.RepoResult(response.body(), "")
                else
                    repoCallback?.RepoResult(null, response.message())

            }
        })
    }
}