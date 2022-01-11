package com.amaar.eilaji

import android.widget.ImageView

data class MedicationInfo(val idDataUser : String = "",
                          val takePhoto : String = "",
                          val describtion : String = "",
                          val firstDay : String = "",
                          val lastDay : String = "",
                          val manyTime : String = ""
)

data class UserInfo (val idUser : String = "" ,
                     val name : String = "",
                     val mobileNumber : String = "",
                     val image : String = ""

)


