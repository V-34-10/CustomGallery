package com.tomgal.customgallery.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomgal.customgallery.R
import com.tomgal.customgallery.databinding.ImageListBinding
import com.tomgal.customgallery.ui.ImageListActivity
import com.tomgal.customgallery.ui.ZoomImageViewActivity
import com.tomgal.customgallery.ui.model.Image

class ImageAdapter(private val context: Context, private val imageModels: List<Image>) :
    RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageListBinding.inflate(inflater, parent, false)
        return ImageHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val imageModel = imageModels[position]
        holder.bind(imageModel)
    }

    override fun getItemCount(): Int {
        return imageModels.size
    }

    inner class ImageHolder(private val binding: ImageListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageModel: Image) {
            Glide.with(context)
                .load(imageModel.regular)
                .centerCrop()
                .placeholder(R.drawable.baseline_broken_image_24)
                .into(binding.imageList)

            binding.imageList.setOnClickListener {
                val intent = Intent(context, ZoomImageViewActivity::class.java).apply {
                    putExtra("small", imageModel.regular)
                }
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }
    }
}