package com.idea.group.phosgithubapp

import android.app.Application
import io.realm.Realm

class PhosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}