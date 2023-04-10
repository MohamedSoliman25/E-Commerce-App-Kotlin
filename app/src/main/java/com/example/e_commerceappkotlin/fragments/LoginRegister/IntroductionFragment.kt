package com.example.e_commerceappkotlin.fragments.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.activities.ShoppingActivity
import com.example.e_commerceappkotlin.databinding.FragmentIntroductionBinding
import com.example.e_commerceappkotlin.viewmodel.IntroductionViewModel
import com.example.e_commerceappkotlin.viewmodel.IntroductionViewModel.Companion.ACCOUNT_OPTION_FRAGMENT
import com.example.e_commerceappkotlin.viewmodel.IntroductionViewModel.Companion.Shopping_Activity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class IntroductionFragment :Fragment() {
    private lateinit var binding:FragmentIntroductionBinding
    private val viewModel by viewModels<IntroductionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroductionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collect {
                when(it){
                    Shopping_Activity ->{

                        Intent(requireActivity(),ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                    }
                }
                    ACCOUNT_OPTION_FRAGMENT ->{
                        findNavController().navigate(it)
                    }
                }
            }
        }
        binding.btnFirstscreen.setOnClickListener{
            viewModel.startButtonClick()
            findNavController().navigate(R.id.action_introductionFragment_to_accountOptionsFragment2)
        }
    }
}