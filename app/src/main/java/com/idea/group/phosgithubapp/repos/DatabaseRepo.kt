package com.idea.group.phosgithubapp.repos

import com.idea.group.phosgithubapp.repos.Interfaces.IDatabaseRepository
import com.idea.group.phosgithubapp.repos.models.DatabaseUpdate
import io.realm.Realm
import io.realm.RealmObject

class DatabaseRepository : IDatabaseRepository {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun <T : RealmObject?> makeObjectManaged(item: T): T {
        return realm.copyToRealm(item)
    }

    override fun <T : RealmObject?> getUnmanagedObjects(type: Class<T>?): List<T>? {
        if(type == null)
            return null

        val query = realm.where(type)
        return if (query.findAll() == null) null else realm.copyFromRealm(query.findAll())
    }

    override fun <T : RealmObject?> getUnmanagedObject(type: Class<T>?): T? {
        if(type == null)
            return null

        val query = realm.where(type)
        val result = query.findFirst()

        return if(result == null)
            null
        else
            if (query.findFirst() == null) null else realm.copyFromRealm(result)
    }

    override fun <T : RealmObject?> updateOrInsertItem(item: MutableCollection<T>?) {
        realm.beginTransaction()
        realm.insertOrUpdate(item)
        realm.commitTransaction()
    }

    override fun <T : RealmObject?> updateOrInsertItem(item: T) {
        realm.beginTransaction()
        realm.insertOrUpdate(item)
        realm.commitTransaction()
    }

    override fun <T : RealmObject?> update(update: DatabaseUpdate?) {
        realm.beginTransaction()
        update?.update()
        realm.commitTransaction()
    }

    override fun <T : RealmObject?> getItem(type: Class<T>?): T? {
        val query = realm.where(type)
        return query.findFirst()
    }

    override fun <T : RealmObject?> getItems(type: Class<T>?): List<T>? {
        val query = realm.where(type)
        return query.findAll()
    }

    override fun <T : RealmObject?> saveItem(item: T) {
        realm.beginTransaction()
        realm.copyToRealm(item)
        realm.commitTransaction()
    }

    override fun <T : RealmObject?> saveItems(items: List<T>?) {
        realm.beginTransaction()
        if (items != null) {
            for (item in items) {
                realm.copyToRealm(item)
            }
        }
        realm.commitTransaction()
    }

    override fun <T : RealmObject?> remove(item: Class<T>?) {
        realm.beginTransaction()
        realm.delete(item)
        realm.commitTransaction()
    }

    override fun <T : RealmObject?> dropDatabase() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

}
