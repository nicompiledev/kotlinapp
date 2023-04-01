package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "student.db"
        private const val TBL_STUDENT = "tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val LASTNAME = "lastname"
        private const val ADDRESS = "address"
        private const val OCCUPATION = "occupation"
        private const val PHONE = "phone"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent = ("CREATE TABLE " + TBL_STUDENT + " ("
                + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, "
                + LASTNAME + " TEXT, " + ADDRESS + " TEXT, "
                + OCCUPATION + " TEXT, " + PHONE + " TEXT, "
                + EMAIL + " TEXT)")
        db?.execSQL(createTblStudent)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL( "DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun insertStudent(std: StudentModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(LASTNAME, std.lastname)
        contentValues.put(ADDRESS, std.address)
        contentValues.put(OCCUPATION, std.occupation)
        contentValues.put(PHONE, std.phone)
        contentValues.put(EMAIL, std.email)

        val success = db.insert(TBL_STUDENT, null, contentValues)
        db.close()
        return success
    }


    fun getAllStudent(): ArrayList<StudentModel>{
        val stdList: ArrayList<StudentModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            return ArrayList()
        }

        var id: Int
        var name: String
        var lastname: String
        var address: String
        var occupation: String
        var phone: String
        var email: String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex(ID))
                name = cursor.getString(cursor.getColumnIndex(NAME))
                lastname = cursor.getString(cursor.getColumnIndex(LASTNAME))
                address = cursor.getString(cursor.getColumnIndex(ADDRESS))
                occupation = cursor.getString(cursor.getColumnIndex(OCCUPATION))
                phone = cursor.getString(cursor.getColumnIndex(PHONE))
                email = cursor.getString(cursor.getColumnIndex(EMAIL))

                val std = StudentModel(id = id, name = name, lastname = lastname,
                    address = address, occupation = occupation, phone = phone, email = email)
                stdList.add(std)
            }while (cursor.moveToNext())
        }

        return stdList
    }


    fun updateStudent(std: StudentModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)
        contentValues.put(ADDRESS, std.address)
        contentValues.put(OCCUPATION, std.occupation)
        contentValues.put(LASTNAME, std.lastname)
        contentValues.put(PHONE, std.phone)

        val success = db.update(TBL_STUDENT, contentValues, "id=" +std.id, null)
        db.close()
        return success
    }

    fun deleteStudentById(id:Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_STUDENT, "id=$id", null)
        db.close()
        return success
    }
}