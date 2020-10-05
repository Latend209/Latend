package com.devroach.latend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainListAdapter(val profileList: ArrayList<MainListComponent>) : RecyclerView.Adapter<MainListAdapter.CustomViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_list, parent, false)
        return CustomViewHolder(view).apply {

            itemView.setOnClickListener {
                val curPos : Int = adapterPosition//현재의 클릭 포지션
                val profile : MainListComponent = profileList.get(curPos)
                //Toast.makeText(parent.context, "이름 : ${profile.name}\n나이 : ${profile.age}\n직업 : ${profile.job}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onBindViewHolder(holder: MainListAdapter.CustomViewHolder, position: Int) {
        //실제 연결
//        holder.gender.setImageResource(profileList.get(position).gender)
//        holder.name.text = profileList.get(position).name
//        holder.age.text = profileList.get(position).age.toString()
//        holder.job.text = profileList.get(position).job
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val gender = itemView.findViewById<ImageView>(R.id.iv_profile)
//        val name = itemView.findViewById<TextView>(R.id.tv_name)
//        val job = itemView.findViewById<TextView>(R.id.tv_job)
//        val age = itemView.findViewById<TextView>(R.id.tv_age)
    }

}