package com.example.enjoycroatia.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseStorageManager {

    private val TAG = "FirebaseStorageManager"
    private val USERS_COLLECTION = "users"
    private val DESTINATIONS_COLLECTION = "destinations"

    private val db = Firebase.firestore


    fun updateUser(user: User) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        db.collection(USERS_COLLECTION).document(uid).set(user).addOnSuccessListener {
            Log.d(TAG, "User updated with name: ${user.nickName}")
        }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    suspend fun getUserAsync(): User? {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        return db.collection(USERS_COLLECTION).document(userId).get().await().toObject<User>()
    }

    fun getDestinations(): LiveData<List<Destination>> {
        val destinations = db.collection(DESTINATIONS_COLLECTION)
        val listOfDestinations = mutableListOf<Destination>()

        destinations.get().addOnSuccessListener { documents ->
            for (document in documents) {
                listOfDestinations.add(document.toObject())
            }
        }
        val listOfDestinationsLiveData = MutableLiveData<List<Destination>>()
        listOfDestinationsLiveData.postValue(listOfDestinations)

        return listOfDestinationsLiveData
    }

    suspend fun getDestinationsAsync(): List<Destination> {
        val destinations = db.collection(DESTINATIONS_COLLECTION)
        val listOfDestinations = mutableListOf<Destination>()
        destinations.get().addOnSuccessListener { destinations ->
            for (destination in destinations) {
                val destinationTemp = destination.toObject<Destination>()
                destinationTemp.id = destination.id
                listOfDestinations.add(destinationTemp)
            }
        }.await()
        return listOfDestinations
    }

    suspend fun getFavorites(): List<Destination> {
        val destinations = getDestinationsAsync()
        val user = getUserAsync()
        val favoriteDestinations = arrayListOf<Destination>()

        user?.let {
            it.favoriteDestinations.entries.forEach { entries ->
                destinations.forEach { destination ->
                    if (entries.key == destination.id)
                        favoriteDestinations.add(destination)
                }
            }
        }

        return favoriteDestinations
    }


    suspend fun addFavorite(destination: Destination) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val user = getUserAsync()
        val map = mutableMapOf(Pair(destination.id, ""))

        user?.let { userTemp ->
            userTemp.favoriteDestinations.entries.forEach {
                map[it.key] = it.value
            }
            user.favoriteDestinations = map

            db.collection(USERS_COLLECTION).document(userId).set(user).addOnSuccessListener {
                Log.d(TAG, "Favorite added!")
            }

        }
    }

    suspend fun removeFavorite(destination: Destination) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val user = getUserAsync()

        user?.let { userTemp ->
            val map = userTemp.favoriteDestinations.toMutableMap()
            userTemp.favoriteDestinations.entries.forEach {
                if (destination.id == it.key) {
                    map.remove(it.key)
                }
            }
            user.favoriteDestinations = map

            db.collection(USERS_COLLECTION).document(userId).set(user).addOnSuccessListener {
                Log.d(TAG, "Favorite removed!")
            }

        }
    }

    suspend fun setVisitedDate(destination: Destination, date: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val user = getUserAsync()

        user?.let { userTemp ->
            val map = userTemp.favoriteDestinations.toMutableMap()
            userTemp.favoriteDestinations.entries.forEach {
                if (destination.id == it.key) {
                    map[it.key] = date
                }
            }
            user.favoriteDestinations = map

            db.collection(USERS_COLLECTION).document(userId).set(user).addOnSuccessListener {
                Log.d(TAG, "Added visited date")
            }
        }

    }

}