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
import com.example.kotlinfitnessapp.model.Exercise


class ExerciseAdapter(val context: Context, val clickListener: OnItemClickListener): RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    private  var exerciseList:List<Exercise>?=null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
        val repsTextView:TextView = view.findViewById(R.id.repsExcName)
        val restTextView:TextView = view.findViewById(R.id.restExsItem)
        val weightTextView:TextView = view.findViewById(R.id.weightExsItem)
        val setsTextView:TextView = view.findViewById(R.id.setsExsItem)
        val deleteButton:ImageButton = view.findViewById(R.id.deleteExericse)
        val nameTextView:TextView = view.findViewById(R.id.textView3)
        val image: ImageView = view.findViewById(R.id.imageView2)
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.onClick(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList!![position]
        holder.nameTextView.text = exercise.exerciseName
        holder.repsTextView.text = exercise.reps.toString()
        holder.restTextView.text = exercise.rest.toString()
        holder.setsTextView.text = exercise.sets.toString()
        holder.weightTextView.text = exercise.weight.toString()
        holder.deleteButton.setOnClickListener{
            clickListener.deleteExercise(position)
        }
        checkPicture(holder,position)
    }

    private fun checkPicture(holder: ViewHolder, position: Int) {
        when {
            ((position + 1) % 8 == 0)-> holder.image.setImageResource (R.drawable.dbls)
            ((position+1) % 7 == 0) -> holder.image.setImageResource(R.drawable.dbls)
            ((position+1) % 6 == 0) -> holder.image.setImageResource(R.drawable.chest)
            ((position+1) % 5 == 0) -> holder.image.setImageResource(R.drawable.leg1)
            ((position+1) % 4 == 0) -> holder.image.setImageResource(R.drawable.arm2)
            ((position+1) % 3 == 0) -> holder.image.setImageResource(R.drawable.leg2)
            ((position+1) % 2 == 0) -> holder.image.setImageResource(R.drawable.pullup)
            else -> holder.image.setImageResource(R.drawable.arm)
        }
    }

    override fun getItemCount(): Int {
        return if(exerciseList==null) 0
        else exerciseList!!.size

    }

    fun setList(list: List<Exercise>?) {
        exerciseList=list
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onClick(num: Int)
        fun deleteExercise(num: Int)
    }
}