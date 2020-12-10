package com.origogi.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.origogi.myapplication.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageUrl : String = intent.extras?.get("imageUrl") as String
        Glide.with(this)
            .load(imageUrl)
            .into(image)
    }
}