package com.example.kotlinfitnessapp.exercises

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.adapters.ExerciseAdapter
import com.example.kotlinfitnessapp.databinding.ActivityExercisesBinding
import com.example.kotlinfitnessapp.databinding.UpdateExerciseBinding
import com.example.kotlinfitnessapp.model.Exercise
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExercisesActivity : AppCompatActivity(), ExerciseAdapter.OnItemClickListener{
    private lateinit var binding:ActivityExercisesBinding
    private lateinit var updateBinding: UpdateExerciseBinding
    private lateinit var adapter:ExerciseAdapter
    private lateinit var exerciseList:List<Exercise>
    private lateinit var workoutId:String
    private var exerciseId:String = ""
    private val viewModel:ExerciseViewModel by viewModels()

    private val groupParent: ViewGroup?=null
    private var dialog: AlertDialog? = null
    private var myView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExercisesBinding.inflate(layoutInflater)
        adapter= ExerciseAdapter(applicationContext,this)
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.myPurple)
        setContentView(binding.root)
        setUpRecView()
        onClicks()
        observe()

    }

    private fun onClicks() {
        binding.addExs.setOnClickListener{
            openUpdate()
            updateBinding.updateButton.text=getString(R.string.Create)
        }
        binding.deleteWorkoutFB.setOnClickListener{
            viewModel.deleteWorkout(workoutId)
        }
    }

    private fun observe() {
        intent.getStringExtra("workoutId")?.let {
            viewModel.setExerciseList(it)
            workoutId=it
        }
        viewModel.getExerciseList().observe(this,{list->
            exerciseList=list
            adapter.setList(list)
        })
        viewModel.getToastMessage().observe(this,{ s->
            when(s){
                "none"->viewModel
                "Done"->{
                    Toast.makeText(applicationContext,s,Toast.LENGTH_SHORT).show()
                    viewModel.setToastMessage("none")
                    dialog!!.dismiss()
                }
                else->{
                    Toast.makeText(applicationContext,s,Toast.LENGTH_SHORT).show()
                    viewModel.setToastMessage(s)
                }
            }
        })
    }

    private fun setUpRecView() {
        binding.recylclerViewEx.layoutManager = LinearLayoutManager(applicationContext)
        binding.recylclerViewEx.adapter= adapter
    }

    private fun openUpdate() {
        val myDialog= AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        myView = inflater.inflate(R.layout.update_exercise, groupParent)
        myDialog.setView(myView)
        dialog = myDialog.create()
        dialog!!.show()
        setOnClicksMyView()
    }

    private fun setOnClicksMyView() {
        updateBinding= myView?.let { UpdateExerciseBinding.bind(it) }!!
        updateBinding.updateButton.setOnClickListener{
            viewModel.updateExercise(
                exerciseId,
                workoutId,
                updateBinding.uExerciseNameEditText.text.toString(),
                updateBinding.updateSetsTextView.text.toString(),
                updateBinding.updateWeightTextView.text.toString(),
                updateBinding.uRepsEditText.text.toString(),
                updateBinding.uRestEditText.text.toString()
                )
        }
        updateBinding.uSetsLeft.setOnClickListener{
            updateValue(-1,updateBinding.updateSetsTextView)
        }
        updateBinding.uSetsRight.setOnClickListener{
            updateValue(1,updateBinding.updateSetsTextView)
        }
        updateBinding.uWeightRight.setOnClickListener{
            updateValue(5,updateBinding.updateWeightTextView)
        }
        updateBinding.uWeightLeft.setOnClickListener{
            updateValue(-5,updateBinding.updateWeightTextView)
        }
    }

    private fun updateValue(i: Int, updateTextView: TextView) {
        var currentNum =Integer.parseInt(updateTextView.text.toString())
        currentNum+=i
        if(currentNum<1)return
        updateTextView.text= currentNum.toString()
    }


    override fun onClick(num: Int) {
        val exercise = exerciseList[num]
        openUpdate()
        setUpForUpdate(exercise)
    }

    override fun deleteExercise(num: Int) {
        val exercise = exerciseList[num]
        viewModel.deleteExercise(exercise.id)
    }

    private fun setUpForUpdate(exercise: Exercise) {
        updateBinding.updateSetsTextView.text = exercise.sets.toString()
        updateBinding.updateWeightTextView.text = exercise.weight.toString()
        updateBinding.uExerciseNameEditText.setText(exercise.exerciseName)
        updateBinding.uRestEditText.setText(exercise.rest.toString())
        updateBinding.uRepsEditText.setText(exercise.rest.toString())
        exerciseId=exercise.id
    }
}