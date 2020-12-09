package com.origogi.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.log.Logger
import com.origogi.myapplication.viewmodel.ImageDataViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this@MainActivity).get(ImageDataViewModel::class.java)

        viewModel.getAll().observe(this, Observer<List<ImageData>> { list ->
            list.forEach {
                Logger.d(it.toString())
            }
        })


    }
}