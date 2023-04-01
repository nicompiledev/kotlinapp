package com.example.myapplication
import java.util.Random




data class StudentModel(
    var id: Int = getAutoId(),
    var name: String = "",
    var lastname: String = "",
    var address: String = "",
    var occupation: String = "",
    var phone: String = "",
    var email: String = ""
) {
    companion object{
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }

}