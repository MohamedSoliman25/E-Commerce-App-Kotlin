package com.example.e_commerceappkotlin.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.adapters.BestProductAdapter
import com.example.e_commerceappkotlin.databinding.FragmentBaseCategoryBinding

open class BaseCategoryFragment():Fragment(R.layout.fragment_base_category) {
    private lateinit var binding:FragmentBaseCategoryBinding
    private lateinit var offerAdapter:BestProductAdapter
    private lateinit var bestProductAdapter:BestProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOfferRv()
        setBestProductRv()
    }

    private fun setBestProductRv() {
        bestProductAdapter = BestProductAdapter()
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)


            adapter = bestProductAdapter
        }         }

    private fun setUpOfferRv() {

    offerAdapter = BestProductAdapter()
        binding.rvOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
        adapter = offerAdapter
        }
    }
}