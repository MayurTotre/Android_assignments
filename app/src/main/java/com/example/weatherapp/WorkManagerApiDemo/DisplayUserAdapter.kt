package com.example.weatherapp.WorkManagerApiDemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class DisplayUserAdapter(private val users: List<User>, val ref: onClickDelete): RecyclerView.Adapter<DisplayUserAdapter.UserViewHolder>() {

    interface onClickDelete {
        fun deleteUser(user: User)
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_view, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.findViewById<CardView>(R.id.cvUserData).setOnClickListener {
            ref.deleteUser(user)
        }
        holder.itemView.findViewById<TextView>(R.id.tvUserName).text = "${user.first_name} ${user.last_name}"
        holder.itemView.findViewById<TextView>(R.id.tvMail).text = user.email
        holder.itemView.findViewById<TextView>(R.id.tvPhoneNo).text = user.phone_no.toString()
        holder.itemView.findViewById<TextView>(R.id.tvFlag).text = user.flag.toString()
    }
}