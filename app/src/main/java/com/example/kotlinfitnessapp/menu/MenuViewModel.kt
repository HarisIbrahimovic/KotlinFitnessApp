package com.example.kotlinfitnessapp.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfitnessapp.model.DietDay
import com.example.kotlinfitnessapp.model.User
import com.example.kotlinfitnessapp.model.Workout
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val menuRepository: MenuRepository):  ViewModel() {

    //incomingLiveData
    private var listOfWorkouts:LiveData<List<Workout>> = menuRepository.getWorkouts()
    private var listOfDays:LiveData<List<DietDay>> =menuRepository.getAllDays()
    private var currentUser:LiveData<User> = menuRepository.getCurrentUser()
    private var toastMessage = MutableLiveData<String>()
    //MutableLiveData
    private var fragmentNum = MutableLiveData<Int>()

    private lateinit var dietDay: DietDay

    fun deleteWorkout(id:String)=viewModelScope.launch(Dispatchers.IO){menuRepository.deleteWorkout(id)}
    fun setFragmentNum(num:Int){fragmentNum.value=num}
    fun getListOfWorkouts() = listOfWorkouts
    fun getToastMessage() = toastMessage
    fun getCurrentUser() = currentUser
    fun getFragmentNum() = fragmentNum
    fun getAllDays()= listOfDays


    init {
        toastMessage.value="none"

    }

    fun setToastMessage(s: String) {
        toastMessage.value=s
    }

    fun deleteDay(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.deleteDay(id)
        }
    }

    fun deletData(){
        FirebaseAuth.getInstance().signOut()
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.deleteData()
        }
    }

    fun addDay(id:String?,protein: String, carbs: String, fats: String) {
        if(protein.isEmpty()||carbs.isEmpty()||fats.isEmpty()){
            toastMessage.value="Fill in the fields."
            return
        }
        val numProtein = Integer.parseInt(protein)
        val numCarbs = Integer.parseInt(carbs)
        val numFats = Integer.parseInt(fats)
        val numCalories = ((numProtein+numCarbs)*4 + numFats*0.7).toInt()
        dietDay =
            if(id==null) DietDay(generateRandomString(),numProtein,numCarbs,numCalories,numFats)
            else DietDay(id,numProtein,numCarbs,numCalories,numFats)
        toastMessage.value="Day added."
        viewModelScope.launch {
            menuRepository.addDay(dietDay)
        }
    }

    private fun generateRandomString(len: Int = 30): String{
        val alphanumerics = CharArray(26) { (it + 97).toChar() }.toSet()
            .union(CharArray(9) { (it + 48).toChar() }.toSet())
        return (0 until len).map {
            alphanumerics.toList().random()
        }.joinToString("")
    }

}