package com.toquemedia.ekklesia.dao

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

abstract class FirebaseFirestoreDao {
    val db = Firebase.firestore

    suspend fun save(collection: String, data: Any) {
        db.collection(collection).add(data).await()
    }

    suspend fun save(collection: String, id: String, data: Any) {
        db.collection(collection).document(id).set(data).await()
    }

    suspend fun saveSubCollection(collection: String, id: String, subCollection: String, subId: String, data: Any) {
        db.collection(collection).document(id).collection(subCollection).document(subId).set(data).await()
    }

    suspend inline fun <reified T> getAll(collection: String): List<T>  {
        return db.collection(collection).get().await().toObjects(T::class.java)
    }

    suspend inline fun <reified T> getAllWithQuery(collection: String, whereEmail: String): List<T> {
        val snapshot = db.collection(collection)
            .whereNotEqualTo("email", whereEmail)
            .get()
            .await()
        return snapshot.toObjects(T::class.java)
    }

    suspend inline fun <reified T> getOnSubCollection(collection: String, docId: String, subCollection: String): List<T> {
        val snapshot = db.collection(collection)
            .document(docId)
            .collection(subCollection)
            .get()
            .await()
        return snapshot.toObjects(T::class.java)
    }

    suspend fun remove(collection: String, id: String) {
        db.collection(collection).document(id).delete().await()
    }

    suspend fun removeSubCollection(collection: String, id: String, subCollection: String, subId: String) {
        db.collection(collection).document(id).collection(subCollection).document(subId).delete().await()
    }
}