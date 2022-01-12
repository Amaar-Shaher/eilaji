package com.amaar.eilaji

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue

class MyViewModel : ViewModel() {
//    var index = 0
    var takePhoto = MutableLiveData("")
    var description = MutableLiveData("")
    var firstDay = MutableLiveData("")
    var lastDay = MutableLiveData("")
    var manyTime = MutableLiveData("")
    private val dataAddDataBase = Firebase.firestore.collection("data medication")


    fun AddToList() {
        var task: DataSource = DataSource()
        task.add(MedicationInfo(takePhoto.value.toString(), description.value.toString(), firstDay.value.toString(),
        lastDay.value.toString(), manyTime.value.toString()))
    }
    fun addNew(medicationInfo: MedicationInfo , image: Uri){
        viewModelScope.launch {
            saveAddDataUser(medicationInfo , image)
        }
    }

    suspend fun saveAddDataUser(medicationInfo : MedicationInfo , image:Uri){
        upload(image).collect {
            var mad = dataAddDataBase.document()
            medicationInfo.takePhoto=it.toString()
            medicationInfo.idDataUser = mad.id
            mad.set(medicationInfo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "$description")
                    }
                }.addOnFailureListener {
                    Log.e("TAG", "not work")
                }
        }
    }
    fun upload(file:Uri): Flow<Uri> = callbackFlow {
        val nameImage = UUID.randomUUID().toString()
        val storageRef =FirebaseStorage.getInstance().getReference("/medphoto/$nameImage")
        storageRef.putFile(file).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { imageUri->

                Log.e("TAG","imageUrl:$imageUri")
                trySend(imageUri)
            }




        }
            .addOnFailureListener{

            }
        awaitClose {  }
    }



}

