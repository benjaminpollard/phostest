package com.idea.group.phosgithubapp.services

import com.idea.group.phosgithubapp.services.responceModels.GitRepoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface GitHubReposService {
    @GET("users/android/repos")
    fun userAndroidRepos(): Call<List<GitRepoResponse>>
}