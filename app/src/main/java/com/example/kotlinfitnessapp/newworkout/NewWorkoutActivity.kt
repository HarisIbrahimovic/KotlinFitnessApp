package com.example.kotlinfitnessapp.newworkout

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.databinding.ActivityNewWorkoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewWorkoutActivity : AppCompatActivity() {
    private val viewModel : NewWorkoutViewModel by viewModels()
    private lateinit var binding: ActivityNewWorkoutBinding
    private var lastSelected:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.myLightPurple)
        observe()
        onClicks()
    }

    private fun observe() {
        viewModel.getWorkoutName().observe(this,{s->
            binding.workoutNameEditText.setText(s)
        })
        viewModel.getSetsValue().observe(this,{s->
            binding.setsTextView.text=s
        })
        viewModel.getWeightValue().observe(this,{s->
            binding.weightTextView.text=s
        })
        viewModel.getToastMessage().observe(this, { s ->
            when (s) {
                "none"->viewModel
                "Workout created."->{
                    Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
                    binding.createWorkoutButton.text=getString(R.string.Add)
                    viewModel.setToastMessage()
                }
                else -> {
                    Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
                    viewModel.setToastMessage()
                }
            }
        })
    }

    private fun onClicks() {
        binding.setsLeft.setOnClickListener{
            viewModel.addValue(binding.setsTextView.text.toString(),-1,"Sets")
        }
        binding.setsRight.setOnClickListener{
            viewModel.addValue(binding.setsTextView.text.toString(),1,"Sets")
        }
        binding.weightLeft.setOnClickListener{
            viewModel.addValue(binding.weightTextView.text.toString(),-5,"Weight")
        }
        binding.weightRight.setOnClickListener{
            viewModel.addValue(binding.weightTextView.text.toString(),5,"Weight")
        }
        binding.armsAndShoulders.setOnClickListener{
            viewModel.setWorkoutName(binding.armsAndShoulders.text.toString())
            updateLastSelected(binding.armsAndShoulders)
        }
        binding.chest.setOnClickListener{
            viewModel.setWorkoutName(binding.chest.text.toString())
            updateLastSelected(binding.chest)
        }
        binding.chestAndArms.setOnClickListener{
            viewModel.setWorkoutName(binding.chestAndArms.text.toString())
            updateLastSelected(binding.chestAndArms)
        }
        binding.chestAndTriceps.setOnClickListener{
            viewModel.setWorkoutName(binding.chestAndTriceps.text.toString())
            updateLastSelected(binding.chestAndTriceps)
        }
        binding.backAndShoulders.setOnClickListener{
            viewModel.setWorkoutName(binding.backAndShoulders.text.toString())
            updateLastSelected(binding.backAndShoulders)
        }
        binding.arms.setOnClickListener{
            viewModel.setWorkoutName(binding.arms.text.toString())
            updateLastSelected(binding.arms)
        }
        binding.legs.setOnClickListener{
            viewModel.setWorkoutName(binding.legs.text.toString())
            updateLastSelected(binding.legs)
        }
        binding.abdomen.setOnClickListener{
            viewModel.setWorkoutName(binding.abdomen.text.toString())
            updateLastSelected(binding.abdomen)
        }
        binding.cardio.setOnClickListener{
            viewModel.setWorkoutName(binding.cardio.text.toString())
            updateLastSelected(binding.cardio)
        }
        binding.pull.setOnClickListener{
            viewModel.setWorkoutName(binding.pull.text.toString())
            updateLastSelected(binding.pull)
        }
        binding.push.setOnClickListener{
            viewModel.setWorkoutName(binding.push.text.toString())
            updateLastSelected(binding.push)
        }
        binding.shoulders.setOnClickListener{
            viewModel.setWorkoutName(binding.shoulders.text.toString())
            updateLastSelected(binding.shoulders)
        }
        binding.upper.setOnClickListener{
            viewModel.setWorkoutName(binding.upper.text.toString())
            updateLastSelected(binding.upper)
        }

        binding.createWorkoutButton.setOnClickListener{
            viewModel.addWorkout(binding.workoutNameEditText.text.toString())
            viewModel.addExercise(
                binding.exerciseNameEditText.text.toString(),
                binding.setsTextView.text.toString(),
                binding.repsEditText.text.toString(),
                binding.restEditText.text.toString(),
                binding.weightTextView.text.toString())
        }
    }

    private fun updateLastSelected(textView: TextView) {
        if (lastSelected!=null){
            lastSelected!!.setTextColor(ContextCompat.getColor(applicationContext,R.color.myBlue))
        }
        textView.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))
        lastSelected=textView
    }
}