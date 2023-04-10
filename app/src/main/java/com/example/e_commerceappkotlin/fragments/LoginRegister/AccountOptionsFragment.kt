package com.example.e_commerceappkotlin.fragments.LoginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.databinding.FragmentAccountOptionsBinding

class AccountOptionsFragment:Fragment() {

    private lateinit var binding:FragmentAccountOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSplashSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionsFragment2_to_loginFragment)
        }
        binding.btnSplashRegister.setOnClickListener {
            findNavController().navigate(R.id.action_accountOptionsFragment2_to_registerFragment)

        }
    }
}