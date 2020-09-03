package com.idea.group.phosgithubapp.repos.models

abstract class RepoCallBack<T> {
    abstract fun RepoResult(data: T?, error: String?)
}


