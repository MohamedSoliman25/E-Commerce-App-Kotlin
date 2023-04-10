package com.example.e_commerceappkotlin.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.activities.ShoppingActivity
import com.example.e_commerceappkotlin.util.Constants
import com.example.e_commerceappkotlin.util.Constants.INTRODUCTION_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
):ViewModel() {

    private val _navigate = MutableStateFlow<Int>(0)
    val navigate = _navigate

    companion object{
        const val Shopping_Activity = 23
        const val ACCOUNT_OPTION_FRAGMENT = R.id.action_introductionFragment_to_accountOptionsFragment2
    }
    init {
        val isButtonClicked = sharedPreferences.getBoolean(INTRODUCTION_KEY,false)
        val user = firebaseAuth.currentUser

        // it means user is already logged in
        if (user!=null){
            viewModelScope.launch {
                _navigate.emit(Shopping_Activity)
            }
        }
        else if (isButtonClicked){
            viewModelScope.launch {
                _navigate.emit(ACCOUNT_OPTION_FRAGMENT)
            }
        }
        else {
            Unit
        }
    }

    fun startButtonClick(){
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY,true).apply()
    }
}