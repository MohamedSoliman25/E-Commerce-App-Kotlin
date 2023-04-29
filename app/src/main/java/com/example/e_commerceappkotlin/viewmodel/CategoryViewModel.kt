package com.example.e_commerceappkotlin.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceappkotlin.data.Category
import com.example.e_commerceappkotlin.data.Product
import com.example.e_commerceappkotlin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel constructor(
    private val firestore: FirebaseFirestore,
    private val category:Category
):ViewModel() {
    private val TAG = "CategoryViewModel"

    private val _offerProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val offerProducts = _offerProducts.asStateFlow()

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts = _bestProducts.asStateFlow()

    init {
        fetchOfferProducts()
        fetchBestProducts()
    }
    fun fetchOfferProducts(){
        viewModelScope.launch {
            _offerProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category",category.category)
            .whereNotEqualTo("offerPercentage",null)
            .get().addOnSuccessListener {result->

                val products =result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _offerProducts.emit(Resource.Success(products))
                    Log.d(TAG, "fetchOfferProductsMo: " +"Success")
                }

            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _offerProducts.emit(Resource.Error(it.message.toString()))
                    Log.d(TAG, "fetchOfferProductsError: "+it.message.toString())
                }
            }    }

    fun fetchBestProducts(){
        viewModelScope.launch {
            _bestProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category",category.category)
            .whereEqualTo("offerPercentage",null)
            .get().addOnSuccessListener {result->

                val products =result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Success(products))
                    Log.d(TAG, "fetchBestProductsMo: "+"Success")
                }

            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Error(it.message.toString()))
                    Log.d(TAG, "fetchBestProductsError: "+it.message.toString())
                }
            }    }
}