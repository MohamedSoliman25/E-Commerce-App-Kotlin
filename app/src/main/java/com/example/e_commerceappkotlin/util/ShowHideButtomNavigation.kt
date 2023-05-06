package com.example.e_commerceappkotlin.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigation =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
    bottomNavigation.visibility = View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigation =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
    bottomNavigation.visibility = View.VISIBLE
}