package com.origogi.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.origogi.myapplication.log.Logger
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.viewmodel.ImageDataViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val gridLayoutManager = GridLayoutManager(this, SPAN_COUNT_ONE)
    private val itemAdapter = ItemAdapter( gridLayoutManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = gridLayoutManager
        }

        val viewModel = ViewModelProviders.of(this@MainActivity).get(ImageDataViewModel::class.java)

        viewModel.getAll().observe(this, Observer<List<ImageData>> { list ->
            list.forEach {
                Logger.d(it.toString())
            }
            itemAdapter.updateDateSet(list)
        })
    }
}