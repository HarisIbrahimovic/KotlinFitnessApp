package com.example.kotlinfitnessapp.menu.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.adapters.DaysAdapter
import com.example.kotlinfitnessapp.databinding.AddDietDayBinding
import com.example.kotlinfitnessapp.databinding.FragmentNutritionBinding
import com.example.kotlinfitnessapp.databinding.UpdateDayBinding
import com.example.kotlinfitnessapp.menu.MenuViewModel
import com.example.kotlinfitnessapp.model.DietDay
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NutritionFragment : Fragment(R.layout.fragment_nutrition) ,DaysAdapter.OnItemClickListener{
    //View
    private var _binding: FragmentNutritionBinding? = null
    private val binding get() = _binding!!
    private val groupParent:ViewGroup?=null
    private var dialog: AlertDialog? = null
    private var myView: View? = null
    private lateinit var addDayBinding : AddDietDayBinding
    private lateinit var updateDayBinding: UpdateDayBinding
    //viewModel
    private val viewModel: MenuViewModel by viewModels()
    //RecViewAccessories
    private lateinit var listOfDays:List<DietDay>
    private lateinit var adapter:DaysAdapter
    private lateinit var dietDay: DietDay


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutritionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= DaysAdapter(requireActivity().applicationContext, this)
        observe()
        onClicks()
        setUpRecView()
    }

    private fun setUpRecView() {
        binding.myDaysRecView.layoutManager=LinearLayoutManager(activity?.applicationContext)
        binding.myDaysRecView.adapter=adapter
    }

    private fun onClicks() {
        binding.addNewDay.setOnClickListener{
            addDay()
        }
    }
    private fun setOnClicksMyView() {
        addDayBinding.createDayButton.setOnClickListener {
            viewModel.addDay(
                null,
                addDayBinding.editTextProtein.text.toString(),
                addDayBinding.editTextCarbs.text.toString(),
                addDayBinding.editTextFats.text.toString(),
            )
        }
    }

    private fun observe() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner, { user ->
            val numCarbs = "${(user.weight * 32 / 4 - (user.weight * 2 + user.weight * 0.7)).toInt()}g"
            val numFats = "${(user.weight * 0.7).toInt()}g"
            val numCalories = "${user.weight * 32}kcal"
            val numProtein = "${user.weight * 2}g"
            binding.caloriesNutrition.text = numCalories
            binding.proteinNutrition.text = numProtein
            binding.CarbsNutrition.text = numCarbs
            binding.fatsNutrition.text = numFats
        })

        viewModel.getToastMessage().observe(viewLifecycleOwner, { s ->
            when(s){
                "Day added"->{
                    Toast.makeText(activity?.applicationContext, s, Toast.LENGTH_SHORT).show()
                    dialog!!.dismiss()
                    viewModel.setToastMessage("none")
                }
                "none"->viewModel
                else ->{
                    Toast.makeText(activity?.applicationContext, s, Toast.LENGTH_SHORT).show()
                    viewModel.setToastMessage("none")
                }
            }
        })

        viewModel.getAllDays().observe(viewLifecycleOwner, { list ->
            adapter.setList(list)
            listOfDays = list
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun openUpdateFragment() {
        val myDialog= AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        myView = inflater.inflate(R.layout.update_day, groupParent)
        myDialog.setView(myView)
        dialog = myDialog.create()
        dialog!!.show()
        updateDayBinding= UpdateDayBinding.bind(myView!!)
        setUpViewsUpdate()
    }


    private fun addDay() {
        val myDialog= AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(activity)
        myView = inflater.inflate(R.layout.add_diet_day, groupParent)
        myDialog.setView(myView)
        dialog = myDialog.create()
        dialog!!.show()
        addDayBinding = AddDietDayBinding.bind(myView!!)
        setOnClicksMyView()
    }
    private fun setUpViewsUpdate() {
        updateDayBinding.editTextProteinUpdate.setText(dietDay.protein.toString())
        updateDayBinding.editTextCarbsUpdate.setText(dietDay.carbs.toString())
        updateDayBinding.editTextFatsUpdate.setText(dietDay.fats.toString())
        setOnClickUpdate()
    }

    override fun onClick(num: Int) {
        dietDay= listOfDays[num]
        openUpdateFragment()
    }
    private fun setOnClickUpdate() {
        updateDayBinding.updateButtonDay.setOnClickListener {
            viewModel.addDay(
                dietDay.id,
                updateDayBinding.editTextProteinUpdate.text.toString(),
                updateDayBinding.editTextCarbsUpdate.text.toString(),
                updateDayBinding.editTextFatsUpdate.text.toString()
            )
        }
        updateDayBinding.deleteButtonDay.setOnClickListener {
            viewModel.deleteDay(dietDay.id)
            dialog!!.dismiss()
        }
    }
}