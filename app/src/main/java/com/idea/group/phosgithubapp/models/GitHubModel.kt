package com.idea.group.phosgithubapp.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GitHubModel : RealmObject(){
    var commitHash: String = ""
    var commitMessage: String = ""
    var name: String = ""
    @PrimaryKey var id : String = ""
}