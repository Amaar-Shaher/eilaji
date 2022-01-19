package com.amaar.eilaji

import android.widget.ImageView

data class MedicationInfo(
    var takePhoto: String = "",
    val describtion: String = "",
    val firstDay: String = "",
    val lastDay: String = "",
    val manyTime: String = "", var idDataUser: String = ""
)

data class UserInfo(
    val idUser: String = "",
    val name: String = "",
    val mobileNumber: String = "",
    val image: String = ""

)


