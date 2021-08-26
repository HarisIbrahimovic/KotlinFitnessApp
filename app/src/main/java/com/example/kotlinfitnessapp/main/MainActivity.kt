package com.example.kotlinfitnessapp.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kotlinfitnessapp.menu.MenuActivity
import com.example.kotlinfitnessapp.R
import com.example.kotlinfitnessapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.navigationBarColor = ContextCompat.getColor(applicationContext, R.color.myLightPurple)
        setContentView(binding.root)
        observers()
        onClicks()
    }

    private fun onClicks() {
        binding.loginButton.setOnClickListener {
            viewModel.chosePath(
                binding.emailLoginMain.text.toString(),
                binding.passwordLogin.text.toString(),
                binding.userNameLogin.text.toString(),
                binding.weightLogin.text.toString(),
                binding.heightLogin.text.toString(),
                binding.loginButton.text.toString()
            )
        }
        binding.loginTextMain.setOnClickListener{
            viewModel.setStateNum(1)
        }
        binding.SignUpTextMain.setOnClickListener{
            viewModel.setStateNum(2)
        }
    }

    private fun observers() {
        viewModel.getLoggedInState().observe(this, { bool ->
            if (bool) {
                startActivity(Intent(application, MenuActivity::class.java))
                finish()
            }
        })

        viewModel.getToastMessage().observe(this, {
            if(!it.equals("none")) {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                viewModel.setToastMessage("none")
            }
        })
        viewModel.getStateNum().observe(this,{
            setUpColors(it)
        })
    }

    private fun setUpColors(num: Int?) {
        if(num==1){
            binding.loginButton.text=application.resources.getString(R.string.login)
            binding.userNameLogin.visibility= View.GONE
            binding.weightLogin.visibility = View.GONE
            binding.heightLogin.visibility = View.GONE
            binding.loginTextMain.setTextColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
            binding.SignUpTextMain.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))
        }else{
            binding.loginButton.text=application.resources.getString(R.string.sign_up)
            binding.userNameLogin.visibility= View.VISIBLE
            binding.weightLogin.visibility = View.VISIBLE
            binding.heightLogin.visibility = View.VISIBLE
            binding.SignUpTextMain.setTextColor(ContextCompat.getColor(applicationContext,R.color.teal_200))
            binding.loginTextMain.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }
}