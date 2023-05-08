package com.example.e_commerceappkotlin.firebase

import com.example.e_commerceappkotlin.data.CartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseCommon(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val cartCollection =  firestore.collection("user").document(auth.uid!!).collection("cart")
    fun addProductToCart(cartProduct: CartProduct,onResult:(CartProduct?,Exception?)->Unit){

        cartCollection.document().set(cartProduct)
            .addOnSuccessListener {
                onResult(cartProduct,null)
            }
            .addOnFailureListener {
                onResult(null,it)
            }
    }

    fun increaseQuantity(documentId:String,onResult:(String?,Exception?)->Unit){
        // runTransaction : it used for read and write at the same time
        firestore.runTransaction{transition->
            val documentRef = cartCollection.document(documentId)
            val document = transition.get(documentRef)
            val productObj = document.toObject(CartProduct::class.java)
            productObj?.let {cartProduct->
                val newQuantity = cartProduct.quantity + 1
                val newProductObject = cartProduct.copy(quantity = newQuantity)
                transition.set(documentRef,newProductObject)
            }
        }
            .addOnSuccessListener {
                onResult(documentId,null)
            }
            .addOnFailureListener {
                onResult(null,it)
            }
    }
}