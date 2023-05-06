package com.example.e_commerceappkotlin.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.adapters.BestProductAdapter
import com.example.e_commerceappkotlin.databinding.FragmentBaseCategoryBinding
import com.example.e_commerceappkotlin.util.Constants
import com.example.e_commerceappkotlin.util.showBottomNavigationView

open class BaseCategoryFragment():Fragment(R.layout.fragment_base_category) {
    private lateinit var binding:FragmentBaseCategoryBinding
    protected  val offerAdapter:BestProductAdapter by lazy {
        BestProductAdapter()
    }
    protected val  bestProductAdapter:BestProductAdapter by lazy {
        BestProductAdapter()
    }

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


        bestProductAdapter.onItemClick = {
            val b = Bundle().apply {
                putParcelable(Constants.PRODUCT,it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        offerAdapter.onItemClick = {
            val b = Bundle().apply {
                putParcelable(Constants.PRODUCT,it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        binding.rvOffer.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollHorizontally(1) && dx!=0){
                    onOfferPagingRequest()
                }
            }
        })
        binding.nestedScrollBaseCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener({ v, _, scrollY, _, _->

            if (v.getChildAt(0).bottom<=v.height + scrollY){
                        onBestProductsPagingRequest()
            }
        }
        ))
    }

    open fun onOfferPagingRequest(){

    }
    open fun onBestProductsPagingRequest(){

    }

    fun showOfferLoading(){

        binding.bestOfferProgressBar.visibility  = View.VISIBLE
    }
    fun hideOfferLoading(){
        binding.bestOfferProgressBar.visibility  = View.GONE

    }

    fun showBestProductsLoading(){

        binding.bestProductsProgressBar.visibility  = View.VISIBLE
    }
    fun hideBestProductsLoading(){
        binding.bestProductsProgressBar.visibility  = View.GONE

    }

    private fun setBestProductRv() {
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)


            adapter = bestProductAdapter
        }         }

    private fun setUpOfferRv() {

        binding.rvOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false)
        adapter = offerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}