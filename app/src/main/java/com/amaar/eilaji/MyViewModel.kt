package com.amaar.eilaji

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.absoluteValue

class MyViewModel : ViewModel() {
//    var index = 0
    var takePhoto = MutableLiveData("")
    var description = MutableLiveData("")
    var firstDay = MutableLiveData("")
    var lastDay = MutableLiveData("")
    var manyTime = MutableLiveData("")

    fun AddToList() {
        var task: DataSource = DataSource()
        task.add(MedicationInfo(takePhoto.value.toString(), description.value.toString(), firstDay.value.toString(),
        lastDay.value.toString(), manyTime.value.toString()))
    }

}

