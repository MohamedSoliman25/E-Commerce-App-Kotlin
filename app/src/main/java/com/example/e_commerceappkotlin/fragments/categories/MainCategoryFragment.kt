package com.example.e_commerceappkotlin.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.adapters.BestDealsRecyclerAdapter
import com.example.e_commerceappkotlin.adapters.BestProductAdapter
import com.example.e_commerceappkotlin.adapters.SpecialProductAdapter
import com.example.e_commerceappkotlin.databinding.FragmentMainCategoryBinding
import com.example.e_commerceappkotlin.util.Resource
import com.example.e_commerceappkotlin.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
@AndroidEntryPoint
class MainCategoryFragment :Fragment(R.layout.fragment_main_category){
    private lateinit var binding:FragmentMainCategoryBinding
    private lateinit var specialProductAdapter: SpecialProductAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()
    lateinit var bestDealsRecyclerAdapter: BestDealsRecyclerAdapter
    lateinit var bestProductAdapter: BestProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpecialProductRv()
        setUpBestDealsRv()
        setUpBestProductRv()
        lifecycleScope.launchWhenStarted {
            viewModel.specialProducts.collectLatest {
                when (it){
                    is Resource.Loading->{
                        showLoading()
                    }
                    is Resource.Success->{
                        specialProductAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error->{
                        hideLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else ->Unit
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.bestDealProducts.collectLatest {
                when (it){
                    is Resource.Loading->{
                        showLoading()
                    }
                    is Resource.Success->{
                        bestDealsRecyclerAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error->{
                        hideLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else ->Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestProduct.collectLatest {
                when (it){
                    is Resource.Loading->{
                        binding.bestProductsProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success->{
                        bestProductAdapter.differ.submitList(it.data)
                        binding.bestProductsProgressBar.visibility = View.GONE
                    }
                    is Resource.Error->{
                        binding.bestProductsProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else ->Unit
                }
            }
        }

        binding.nestedScrollMainCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener({v,_,scrollY,_,_->

            if (v.getChildAt(0).bottom<=v.height + scrollY){
                viewModel.fetchBestProducts()
            }
            }
        ))

    }

    private fun setUpBestProductRv() {
        bestProductAdapter = BestProductAdapter()
        binding.rvBestProduct.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter = bestProductAdapter
        }       }

    private fun setUpBestDealsRv() {
        bestDealsRecyclerAdapter = BestDealsRecyclerAdapter()
        binding.rvBestDeals.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = bestDealsRecyclerAdapter
        }
    }

    private fun setUpSpecialProductRv(){
        specialProductAdapter = SpecialProductAdapter()
        binding.rvSpecialProduct.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = specialProductAdapter
        }
    }

    private fun showLoading(){

        binding.bestOfferProgressBar.visibility = View.VISIBLE
    }
    private fun hideLoading(){

        binding.bestOfferProgressBar.visibility = View.GONE

    }
}