package com.example.e_commerceappkotlin.data

sealed class Category(val category:String){
    // I make an object inside sealed class where each object is a singleton and has it's own behaviour
    object Chair:Category("Chair")
    object Cupboard:Category("Cupboard")
    object Table:Category("Table")
    object Accessory:Category("Accessory")
    object Furniture:Category("Furniture")

}
