package com.idea.group.phosgithubapp.services

import com.idea.group.phosgithubapp.services.responceModels.GitRepoResponse
import com.idea.group.phosgithubapp.services.responceModels.LastCommitResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface GitHubLastCommitService {
    @GET
    fun userAndroidRepos(@Url url: String): Call<List<LastCommitResponse>>
}