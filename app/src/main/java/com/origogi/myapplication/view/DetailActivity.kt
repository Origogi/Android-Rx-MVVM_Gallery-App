package com.origogi.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.origogi.myapplication.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TEST","Detail onCreate()")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageUrl : String = intent.extras?.get("imageUrl") as String
        Glide.with(this)
            .load(imageUrl)
            .into(image)
    }

    override fun onPause() {
        super.onPause()
        Log.d("TEST","Detail onPause()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST","Detail onResume()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST","Detail onStart()")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TEST","Detail onRestart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST","Detail onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST","Detail onDestroy()")
    }
}