package com.tomgal.customgallery.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.tomgal.customgallery.databinding.ActivityMainBinding
import com.tomgal.customgallery.utils.HideUI

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        HideUI.hideUI(this)
        runListImageActivity()
    }

    private fun runListImageActivity() {
        var startIntent: Intent
        Handler().postDelayed({
            startIntent = Intent(this@MainActivity, ImageListActivity::class.java)
            startActivity(startIntent)
            finish()
        }, 3 * 1000.toLong())
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}