package com.example.e_commerceappkotlin.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceappkotlin.R
import com.example.e_commerceappkotlin.databinding.ColorRvItemBinding
import com.example.e_commerceappkotlin.util.Constants.COLORS_TYPE

class ColorsAdapter() :RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {

    private var selectedPosition = -1

    var onItemClick : ((Int) -> Unit)?=null
    inner class ColorsViewHolder(val binding: ColorRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Int, position: Int) {
            val imageDrawable = ColorDrawable(color)
            binding.imgContent.setImageDrawable(imageDrawable)

            if (position == selectedPosition) { //Select case
                binding.apply {

                    imgShadow.visibility = View.VISIBLE
                    imgPicked.visibility = View.VISIBLE

                }
            } else { //color is not selected
                binding.apply {

                    imgShadow.visibility = View.INVISIBLE
                    imgPicked.visibility = View.INVISIBLE

                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
        }


        val differ = AsyncListDiffer(this,diffCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        return ColorsViewHolder(
            ColorRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {

        val color = differ.currentList[position]
        holder.bind(color,position)
        holder.itemView.setOnClickListener{
            val previousSelectedItem = selectedPosition
            selectedPosition = holder.adapterPosition

            // Notify the adapter of item changes
            notifyItemChanged(previousSelectedItem)
            notifyItemChanged(selectedPosition)
            onItemClick?.invoke(color)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}