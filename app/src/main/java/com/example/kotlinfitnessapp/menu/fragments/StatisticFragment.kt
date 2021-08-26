package com.example.kotlinfitnessapp.menu.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.databinding.FragmentStatisticBinding
import com.example.kotlinfitnessapp.main.MainActivity
import com.example.kotlinfitnessapp.menu.MenuViewModel
import com.example.kotlinfitnessapp.model.DietDay
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.fragment_statistic){

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllDays().observe(viewLifecycleOwner) { dietDays ->
            setUpProteinData(dietDays)
            setUpChart(binding.proteinLineCHart)
            setUpCaloriesData(dietDays)
            setUpChart(binding.lineChart)
        }
        binding.logOutButton.setOnClickListener{
            viewModel.deletData()
            startActivity(Intent(requireActivity().applicationContext,MainActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun setUpChart(chart: LineChart) {
        chart.isDragEnabled = true
        chart.setScaleEnabled(false)
        chart.legend.textColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.white)
        chart.axisLeft.textColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.white)
        chart.xAxis.textColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.white)
        chart.axisRight.setDrawLabels(false)
        chart.lineData.setValueTextSize(0f)
        chart.description.text = ""
    }


    private fun setUpProteinData(dietDays: List<DietDay>) {
        val protenEntries: ArrayList<Entry> = ArrayList()
        var i = 1
        for (dietDay in dietDays) {
            protenEntries.add(Entry(i.toFloat(), dietDay.protein.toFloat()))
            i++
        }
        val set = LineDataSet(protenEntries, "Daily protein")
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        set.fillAlpha = 100
        dataSets.add(set)
        val data = LineData(dataSets)
        binding.proteinLineCHart.invalidate()
        if ( binding.proteinLineCHart.data != null) return
        binding.proteinLineCHart.data = data
    }

    private fun setUpCaloriesData(dietDays: List<DietDay>) {
        val calorieEntries: ArrayList<Entry> = ArrayList()
        var i = 1
        for (dietDay in dietDays) {
            calorieEntries.add(Entry(i.toFloat(), dietDay.calories.toFloat()))
            i++
        }
        val set = LineDataSet(calorieEntries, "Daily calories")
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        set.fillAlpha = 100
        dataSets.add(set)
        val data = LineData(dataSets)
        binding.lineChart.invalidate()
        if (binding.lineChart.data != null) return
        binding.lineChart.data = data
    }
}