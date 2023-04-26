package com.example.e_commerceappkotlin.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceappkotlin.data.Product
import com.example.e_commerceappkotlin.databinding.SpecialRvItemBinding

class SpecialProductAdapter() :
    RecyclerView.Adapter<SpecialProductAdapter.BestProductsRecyclerAdapterViewHolder>() {
    var onItemClick: ((Product) -> Unit)? = null

    inner class BestProductsRecyclerAdapterViewHolder(val binding: SpecialRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestProductsRecyclerAdapterViewHolder {
        return BestProductsRecyclerAdapterViewHolder(
            SpecialRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BestProductsRecyclerAdapterViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(product.images[0]).into(imgAd)
            tvAdName.text = product.name
            tvAdPrice.text = "$${product.price}"
//            tvNewPrice.visibility = View.GONE
        }

//        product.newPrice?.let {
//            if (product.newPrice.isNotEmpty() && product.newPrice != "0") {
//                holder.binding.apply {
//                    tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                    tvNewPrice.text = "$${product.newPrice}"
//                    tvNewPrice.visibility = View.VISIBLE
//                }
//            }
//        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}