package com.example.e_commerceappkotlin.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceappkotlin.data.Product
import com.example.e_commerceappkotlin.databinding.BestDealItemBinding

class BestDealsRecyclerAdapter :
    RecyclerView.Adapter<BestDealsRecyclerAdapter.BestDealsRecyclerAdapterViewHolder>() {

    inner class BestDealsRecyclerAdapterViewHolder(val binding: BestDealItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(product: Product){
                binding.apply {
                    Glide.with(itemView).load(product.images[0]).into(imgBestDeal)
                    product.offerPercentage?.let {
                        val remainingPricePercentage = 1f - it
                        val priceAfterOffer = remainingPricePercentage * product.price
                        tvNewPrice.text = "$${String.format("%.2f",priceAfterOffer)}"

                    }
                    tvDealProductName.text = product.name
                    tvOldPrice.text = "$${product.price}"
                    if (product.offerPercentage!=null) {
                        tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }

                binding.btnSeeProduct.setOnClickListener {
                    onItemClick?.invoke(product)
                }
            }
        }

    private val diffCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestDealsRecyclerAdapterViewHolder {
        return BestDealsRecyclerAdapterViewHolder(
            BestDealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestDealsRecyclerAdapterViewHolder, position: Int) {
        val product = differ.currentList[position]
       holder.bind(product)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick: ((Product) -> Unit)? = null


}