package com.example.kotlinfitnessapp.menu

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.databinding.ActivityMenuBinding
import com.example.kotlinfitnessapp.menu.fragments.NutritionFragment
import com.example.kotlinfitnessapp.menu.fragments.StatisticFragment
import com.example.kotlinfitnessapp.menu.fragments.WorkoutFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var fragment:Fragment
    private val viewModel: MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        setUpNavigation()
    }

    private fun setUpNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.workout->viewModel.setFragmentNum(1)
                R.id.nutrition->viewModel.setFragmentNum(2)
                R.id.statistics->viewModel.setFragmentNum(3)
            }
            true
        }
        viewModel.getFragmentNum().observe(this,{num->
            fragment = when(num){
                3-> StatisticFragment()
                2-> NutritionFragment()
                else-> WorkoutFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.menuFragmentFrame,fragment).commit()
        })

    }

    private fun setUpView() {
        binding.bottomNavigationView.itemIconTintList = null
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.myLightPurple)
        supportFragmentManager.beginTransaction().replace(R.id.menuFragmentFrame,WorkoutFragment()).commit()
        if(!isOnline(applicationContext))
            Toast.makeText(applicationContext,"You are offline.",Toast.LENGTH_SHORT).show()
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        when {
            capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)-> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->{
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
           capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
         }
         return false
    }

}