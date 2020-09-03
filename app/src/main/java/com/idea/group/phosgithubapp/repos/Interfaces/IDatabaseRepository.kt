package com.idea.group.phosgithubapp.repos.Interfaces

import com.idea.group.phosgithubapp.repos.models.DatabaseUpdate
import io.realm.RealmObject

interface IDatabaseRepository {
    fun <T : RealmObject?> makeObjectManaged(item: T): T?
    fun <T : RealmObject?> getUnmanagedObjects(type: Class<T>?): List<T>?
    fun <T : RealmObject?> getUnmanagedObject(type: Class<T>?): T?
    fun <T : RealmObject?> updateOrInsertItem(item: MutableCollection<T>?)
    fun <T : RealmObject?> updateOrInsertItem(item: T)
    fun <T : RealmObject?> getItem(type: Class<T>?): T?
    fun <T : RealmObject?> getItems(type: Class<T>?): List<T>?
    fun <T : RealmObject?> saveItem(item: T)
    fun <T : RealmObject?> saveItems(items: List<T>?)
    fun <T : RealmObject?> update(update: DatabaseUpdate?)
    fun <T : RealmObject?> remove(item: Class<T>?)
    fun <T : RealmObject?> dropDatabase()
}

