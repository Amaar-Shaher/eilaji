package com.amaar.eilaji

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue

class MyViewModel : ViewModel() {

    private val dataAddDataBase = Firebase.firestore.collection("data medication")


    fun addNew(medicationInfo: MedicationInfo, image: Uri) {
        viewModelScope.launch {
            saveAddDataUser(medicationInfo, image)
        }
    }

    fun deleteMed(medicationInfo: MedicationInfo) {
        viewModelScope.launch {
            delete(medicationInfo)
        }
    }

    fun editmed(medicationInfo: MedicationInfo, image: Uri?) {
        viewModelScope.launch {
            editMedication(medicationInfo, image)
            Log.e("TAG", "editmed:${medicationInfo.describtion} ")
        }
    }

    suspend fun saveAddDataUser(medicationInfo: MedicationInfo, image: Uri) {
        upload(image).collect {
            // val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val mad = dataAddDataBase.document()
            medicationInfo.takePhoto = it.toString()
            medicationInfo.idDataUser = mad.id
            // mu list
            //  list.value.add(medicationInfo)
            mad.set(medicationInfo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                    }
                }.addOnFailureListener {
                }
        }
    }

    fun upload(file: Uri): Flow<Uri> = callbackFlow {
        val nameImage = UUID.randomUUID().toString()
        val storageRef = FirebaseStorage.getInstance().getReference("/medphoto/$nameImage")
        storageRef.putFile(file).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { imageUri ->

                trySend(imageUri)
            }


        }
            .addOnFailureListener {

            }
        awaitClose { }
    }


    suspend fun editMedication(medicationInfo: MedicationInfo, image: Uri?) {

        if (image != null) {
            upload(image).collect {
//            val db = Firebase.firestore
                medicationInfo.takePhoto = it.toString()
//               medicationInfo.idDataUser = id

                dataAddDataBase.document(medicationInfo.idDataUser)
                    .set(medicationInfo)
                    .addOnCompleteListener { documentReference ->
                    }
                    .addOnFailureListener {

                    }
            }
        } else {

            dataAddDataBase.document(medicationInfo.idDataUser)
                .set(medicationInfo)
                .addOnCompleteListener { documentReference ->
                }
        }
    }


    suspend fun delete(medicationInfo: MedicationInfo) {
        dataAddDataBase.document(medicationInfo.idDataUser)
            .delete()

    }


}

