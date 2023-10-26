package com.firstdueapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.firstdueapplication.R
import com.firstdueapplication.databinding.ItemProductBinding
import com.firstdueapplication.models.Photo
import com.firstdueapplication.models.Product

class AnimalAdapter(val onProductClick: (Photo) -> Unit) :
    ListAdapter<Photo, AnimalAdapter.ProductViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val animal = getItem(position)
        animal?.let {
            holder.bind(animal)
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Photo) {
            binding.tvTitle.text = animal.title
            binding.tvDescription.text = animal.description
            binding.tvCategory.text = "User: ${animal.id}"
            Glide.with(binding.ivProduct.context).load(animal.url).centerCrop()
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .into(binding.ivProduct)
            binding.root.setOnClickListener {
                onProductClick(animal)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }
}