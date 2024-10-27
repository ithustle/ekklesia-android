package com.toquemedia.ekklesia.dao

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

abstract class FirebaseFirestoreDao {
    private val db = Firebase.firestore

    fun save(collection: String, data: Any) {
        db.collection(collection).add(data)
    }

    fun save(collection: String, id: String, data: Any) {
        db.collection(collection).document(id).set(data)
    }
}