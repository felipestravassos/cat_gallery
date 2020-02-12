package com.fstravassos.catgallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fstravassos.catgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.recyclerViewGallery.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerViewGallery.adapter = GalleryAdapter()

        viewModel.getCatsLiveData().observe(this,
            Observer { (binding.recyclerViewGallery.adapter as GalleryAdapter).reload(it) })
    }
}
