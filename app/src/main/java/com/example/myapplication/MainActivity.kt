package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edLastname: EditText
    private lateinit var edAddress: EditText
    private lateinit var edPhone: EditText
    private lateinit var edOccupation: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener { updateStudent() }
        //Delete record


        adapter?.setOnclickItem {
            Toast.makeText(this, it.name,Toast.LENGTH_SHORT).show()
            // Update record
            edName.setText(it.name)
            edEmail.setText((it.email))
            std = it
        }

        adapter?.setOnclickDeleteItem {
            deleteStudent(it.id)
        }

    }

    private fun getStudents() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size}")

        //Display data in RecyclerView
        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val lastname = edLastname.text.toString()
        val address = edAddress.text.toString()
        val occupation = edOccupation.text.toString()
        val phone = edPhone.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter required field...", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email, lastname = lastname, address = address, occupation = occupation, phone = phone)
            val status = sqLiteHelper.insertStudent(std)
            // Check insert success or not success
            if (status > -1) {
                Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val lastname = edLastname.text.toString()
        val address = edAddress.text.toString()
        val occupation = edOccupation.text.toString()
        val phone = edPhone.text.toString()

        //Check record not change
        if (name == std?.name && email == std?.email && lastname == std?.lastname && address == std?.address && occupation == std?.occupation && phone == std?.phone){
            Toast.makeText( this, "Record not changed...", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email = email, lastname = lastname, address = address, occupation = occupation, phone = phone)
        val status = sqLiteHelper.updateStudent(std)
        if(status > -1){
            clearEditText()
            getStudents()
        }else{
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }


    private fun deleteStudent(id:Int){
        //if(id== null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setCancelable(true)
        builder.setPositiveButton( "Yes"){ dialog, _ ->
            sqLiteHelper.deleteStudentById(id)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton( "No"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edAddress.setText("")
        edPhone.setText("")
        edLastname.setText("")
        edOccupation.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =  StudentAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        edLastname = findViewById(R.id.edLastname)
        edAddress = findViewById(R.id.edAddress)
        edOccupation = findViewById(R.id.edOccupation)
        edPhone = findViewById(R.id.edPhone)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}