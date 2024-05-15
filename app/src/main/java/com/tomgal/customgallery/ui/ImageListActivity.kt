package com.tomgal.customgallery.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.tomgal.customgallery.databinding.ActivityImageListBinding
import com.tomgal.customgallery.ui.adapter.ImageAdapter
import com.tomgal.customgallery.ui.model.Image
import com.tomgal.customgallery.utils.HideUI
import org.json.JSONException

class ImageListActivity : AppCompatActivity() {
    private val binding by lazy { ActivityImageListBinding.inflate(layoutInflater) }
    private var list: MutableList<Image> = mutableListOf()
    private var imageAdapter: ImageAdapter? = null
    private var urls =
        "https://api.unsplash.com/photos/?client_id=pGL47fw2FucX3rZ7h_tPXWzG98yhq0pPRZxh5V5Alhk&page="
    private var page = 1
    private val perPage = "&per_page=30"
    private var scrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        HideUI.hideUI(this)
        initRecycler()
        fetchWallPaper()
    }

    private fun initRecycler() {
        imageAdapter = ImageAdapter(this, list)
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerListImage.layoutManager = gridLayoutManager
        binding.recyclerListImage.adapter = imageAdapter
        binding.recyclerListImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrolling = newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItem = gridLayoutManager.childCount
                val totalItem = gridLayoutManager.itemCount
                val scrollOut = gridLayoutManager.findFirstVisibleItemPosition()
                if (scrolling && currentItem + scrollOut == totalItem) {
                    scrolling = false
                    fetchWallPaper()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchWallPaper() {
        val url = "$urls$page$perPage"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val imageUrls = jsonObject.getJSONObject("urls")
                        val urls = imageUrls.getString("small")
                        val imageModel = Image(urls)
                        list.add(imageModel)
                    }
                    imageAdapter?.notifyDataSetChanged()
                    binding.progressCircular.visibility = View.GONE
                    page++
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonArrayRequest)
    }

    override fun onResume() {
        super.onResume()
        HideUI.hideUI(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}