package com.tomgal.customgallery.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.tomgal.customgallery.R
import com.tomgal.customgallery.databinding.ActivityZoomImageViewBinding
import com.tomgal.customgallery.utils.HideUI

class ZoomImageViewActivity : AppCompatActivity() {
    private val binding by lazy { ActivityZoomImageViewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        HideUI.hideUI(this)

        intent.extras?.getString("small")?.let { imageUrl ->
            loadImage(imageUrl)
        }
    }

    override fun onResume() {
        super.onResume()
        HideUI.hideUI(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@ZoomImageViewActivity, ImageListActivity::class.java))
        finish()
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.baseline_broken_image_24)
            .into(binding.fullImage)
    }
}