package com.example.kotlinfitnessapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.model.DietDay


class DaysAdapter(private val context: Context, private val clickListener: OnItemClickListener): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    private  var listOfDays:List<DietDay>?=null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener {
        val proteinText:TextView = view.findViewById(R.id.proteinItem)
        val carbsText:TextView = view.findViewById(R.id.carbsItem)
        val fatsText:TextView = view.findViewById(R.id.fatsItem)
        val caloriesText:TextView = view.findViewById(R.id.caloriesItem)
        val image:ImageView = view.findViewById(R.id.imageNutritionItem)
        init{
            view.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            clickListener.onClick(adapterPosition)
        }
    }

    fun setList(list: List<DietDay>){
        listOfDays=list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.day_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = listOfDays?.get(position)
        holder.caloriesText.text= day?.calories.toString()
        holder.proteinText.text= day?.protein.toString()
        holder.carbsText.text= day?.carbs.toString()
        holder.fatsText.text= day?.fats.toString()
        checkPicture(holder, position)
    }

    override fun getItemCount(): Int {
        if(listOfDays==null)return 0
        return listOfDays!!.size
    }

    interface OnItemClickListener{
        fun onClick(num: Int)
    }

    private fun checkPicture(holder: ViewHolder, position: Int) {
        when {
            ((position + 1) % 6 == 0) -> holder.image.setImageResource(R.drawable.shake)
            ((position + 1) % 5 == 0) -> holder.image.setImageResource(R.drawable.cocoa)
            ((position + 1) % 4 == 0) -> holder.image.setImageResource(R.drawable.almond)
            ((position + 1) % 3 == 0) -> holder.image.setImageResource(R.drawable.chilli)
            ((position + 1) % 2 == 0) -> holder.image.setImageResource(R.drawable.bacon)
            else -> holder.image.setImageResource(R.drawable.bananas)
        }
    }

}