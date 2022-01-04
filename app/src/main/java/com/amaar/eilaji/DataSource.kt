package com.amaar.eilaji

import android.util.Log

class DataSource {
    fun loadHomePageFragment(): List<MedicationInfo> {
        return MedicationList

    }
    fun add (task: MedicationInfo){
        MedicationList.add(task)
    }
    fun deleteTask(index:Int){
        MedicationList.removeAt(index)
    }
    fun updateTask(index: Int, task: MedicationInfo){
        MedicationList.set(index, task)
      Log.d("TAG", "updateTask: $MedicationList")
    }


}