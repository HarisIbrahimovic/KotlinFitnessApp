package com.example.kotlinfitnessapp.menu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.adapters.WorkoutAdapter
import com.example.kotlinfitnessapp.databinding.FragmentWorkoutBinding
import com.example.kotlinfitnessapp.exercises.ExercisesActivity
import com.example.kotlinfitnessapp.menu.MenuViewModel
import com.example.kotlinfitnessapp.model.Workout
import com.example.kotlinfitnessapp.newworkout.NewWorkoutActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutFragment : Fragment(R.layout.fragment_workout),WorkoutAdapter.OnItemClickListener {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()
    private lateinit var listOfWorkouts:List<Workout>
    private lateinit var adapter:WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWorkoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addWorkoutBotton.setOnClickListener{
            startActivity(Intent(activity?.applicationContext,NewWorkoutActivity::class.java))
        }
        observe()
        adapter = WorkoutAdapter(requireActivity().applicationContext,this)
        binding.myWorkoutsRecyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.myWorkoutsRecyclerView.adapter=adapter

    }

    private fun observe() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner,{
            if(it!=null)
                binding.userNameWorkoutFrag.text=it.username
        })
        viewModel.getListOfWorkouts().observe(viewLifecycleOwner,{list->
            adapter.setList(list)
            listOfWorkouts= list
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onClick(num: Int) {

        val intent = Intent(activity,ExercisesActivity::class.java)
        intent.putExtra("workoutId",listOfWorkouts[num].id)
        intent.putExtra("workoutName",listOfWorkouts[num].name)
        startActivity(intent)
    }

    override fun onDelete(num: Int) {
        viewModel.deleteWorkout(listOfWorkouts[num].id)
        Toast.makeText(activity?.applicationContext,"Workout removed",Toast.LENGTH_SHORT).show()
    }


}