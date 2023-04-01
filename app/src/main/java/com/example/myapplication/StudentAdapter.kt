package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem:((StudentModel) -> Unit)? = null
    private var onClickDeleteItem:((StudentModel) -> Unit)? = null

    fun addItems(items: ArrayList<StudentModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnclickItem(callback: (StudentModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnclickDeleteItem(callback: (StudentModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder (var view : View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var phone = view.findViewById<TextView>(R.id.tvPhone)
        private var lastname = view.findViewById<TextView>(R.id.tvLastname)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        private var occupation = view.findViewById<TextView>(R.id.tvOccupation)

        var btnDelete: Button = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: StudentModel) {
            id.text = std.id.toString()
            name.text = std.name
            email.text =std.email
            phone.text = std.phone
            lastname.text = std.lastname
            address.text = std.address
            occupation.text = std.occupation
        }

    }
}