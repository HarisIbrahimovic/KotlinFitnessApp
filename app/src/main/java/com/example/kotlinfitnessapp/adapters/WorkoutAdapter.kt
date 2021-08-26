package com.example.kotlinfitnessapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.model.Workout


class WorkoutAdapter(val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    private var listOfWorkouts: List<Workout>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.workout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.workoutName.text = listOfWorkouts?.get(position)?.name
        holder.deleteButton.setOnClickListener{
            onItemClickListener.onDelete(position)
        }
        checkPicture(holder, position)
    }

    override fun getItemCount(): Int {
       if(listOfWorkouts==null)return 0
       return listOfWorkouts!!.size
    }

    fun setList(list: List<Workout>){
        listOfWorkouts = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view), View.OnClickListener {
        val workoutName: TextView = view.findViewById(R.id.textView3)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteImage)
        val image: ImageView = itemView.findViewById(R.id.imageView2)
        init {
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onItemClickListener.onClick(adapterPosition)
        }
    }


    private fun checkPicture(holder: ViewHolder, position: Int) {
        when {
            ((position + 1) % 6 == 0) -> holder.image.setImageResource(R.drawable.cardiopic)
            ((position + 1) % 5 == 0) -> holder.image.setImageResource(R.drawable.masina)
            ((position + 1) % 4 == 0) -> holder.image.setImageResource(R.drawable.latmasina)
            ((position + 1) % 3 == 0) -> holder.image.setImageResource(R.drawable.rings)
            ((position + 1) % 2 == 0) -> holder.image.setImageResource(R.drawable.benc)
            else ->
                holder.image.setImageResource(R.drawable.gympic1)
        }
    }

    interface OnItemClickListener{
        fun onClick(num: Int)
        fun onDelete(num: Int)
    }

}