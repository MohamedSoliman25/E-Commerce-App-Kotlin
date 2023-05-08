package com.example.e_commerceappkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.databinding.SizeRvItemBinding
import com.example.e_commerceappkotlin.fragments.shopping.ProductDetailsFragment

class SizesAdapter(val context: ProductDetailsFragment) :RecyclerView.Adapter<SizesAdapter.SizesViewHolder>(){

    private var selectedPosition = -1

    var onItemClick : ((String) -> Unit)?=null
    inner class SizesViewHolder(private val binding: SizeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(size: String, position: Int) {
         binding.tvSize.text = size

            if (position == selectedPosition) { //size is selected
                binding.apply {

                    imgShadow.visibility = View.VISIBLE

                }
            } else { //size is not selected
                binding.apply {

                    imgShadow.visibility = View.INVISIBLE

                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this,diffCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesViewHolder {
        return SizesViewHolder(
            SizeRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )    }

    override fun onBindViewHolder(holder: SizesViewHolder, position: Int) {

        val size = differ.currentList[position]
        holder.bind(size,position)

        holder.itemView.setOnClickListener{
            val previousSelectedItem = selectedPosition
            selectedPosition = holder.adapterPosition

            // Notify the adapter of item changes
            notifyItemChanged(previousSelectedItem)
            notifyItemChanged(selectedPosition)
            onItemClick?.invoke(size)
//            if (selectedPosition>=0){
//                notifyItemChanged(selectedPosition)
//                selectedPosition = holder.adapterPosition
//                notifyItemChanged(selectedPosition)
//                onItemClick?.invoke(size)
//            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}