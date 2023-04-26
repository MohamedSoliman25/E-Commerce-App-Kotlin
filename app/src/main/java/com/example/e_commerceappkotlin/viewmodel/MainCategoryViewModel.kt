package com.example.e_commerceappkotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceappkotlin.data.Product
import com.example.e_commerceappkotlin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore:FirebaseFirestore
):
    ViewModel() {
        private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts:StateFlow<Resource<List<Product>>> = _specialProducts

    private val _bestDealProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDealProducts:StateFlow<Resource<List<Product>>> = _bestDealProducts

    private val _bestProduct = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProduct:StateFlow<Resource<List<Product>>> = _bestProduct
    private val pagingInfo = PagingInfo()

    init {
        fetchSpecialProducts()
        fetchBestDeals()
        fetchBestProducts()
    }
    fun fetchSpecialProducts(){
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category","Special Products")
            .get().addOnSuccessListener {result->

                val specialProductList =result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductList))
                }

            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }
    fun fetchBestDeals(){
        viewModelScope.launch {
            _bestDealProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category","Best Deals")
            .get().addOnSuccessListener {result->

                val bestDealProducts =result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealProducts.emit(Resource.Success(bestDealProducts))
                }

            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _bestDealProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchBestProducts() {
        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _bestProduct.emit(Resource.Loading())
            }
            firestore.collection("Products")
                .limit(pagingInfo.bestProductPage * 10)
                .get().addOnSuccessListener { result ->

                    val bestProducts = result.toObjects(Product::class.java)
                    //compare current list with old list if equal we don't have any more products and end paging
                    pagingInfo.isPagingEnd = bestProducts == pagingInfo.oldBestProducts
                    pagingInfo.oldBestProducts = bestProducts
                    viewModelScope.launch {
                        _bestProduct.emit(Resource.Success(bestProducts))
                    }
                    pagingInfo.bestProductPage++
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _bestProduct.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }

    internal data class PagingInfo(
         var bestProductPage:Long = 1,
    var oldBestProducts:List<Product> = emptyList(),
    var isPagingEnd: Boolean = false

    )
}