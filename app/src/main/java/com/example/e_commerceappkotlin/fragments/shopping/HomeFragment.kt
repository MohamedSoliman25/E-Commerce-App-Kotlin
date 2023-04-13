package com.example.e_commerceappkotlin.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.adapters.HomeViewpagerAdapter
import com.example.e_commerceappkotlin.databinding.FragmentHomeBinding
import com.example.e_commerceappkotlin.fragments.categories.*
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment :Fragment() {
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )
        val viewPager2Adapter = HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewpagerHome){tab,position->

            when(position){
//                0 -> tab.text = resources.getText(R.string.g_home)
                0 -> tab.text = resources.getText(R.string.main)
                1-> tab.text = resources.getText(R.string.g_chair)
                2-> tab.text = resources.getText(R.string.g_cupboard)
                3-> tab.text = resources.getText(R.string.g_table)
                4-> tab.text = resources.getText(R.string.g_accessory)
                5-> tab.text = resources.getText(R.string.g_furniture)
//                6-> tab.text = resources.getText(R.string.g_enlightening)
            }

        }.attach()
    }
}