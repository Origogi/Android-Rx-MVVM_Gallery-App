package com.origogi.myapplication.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.origogi.myapplication.R
import com.origogi.myapplication.SPAN_COUNT_FOUR
import com.origogi.myapplication.SPAN_COUNT_ONE
import com.origogi.myapplication.STATE
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.viewmodel.ImageDataViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_placeholder.*


class MainActivity : AppCompatActivity() {
    private val gridLayoutManager = GridLayoutManager(
        this,
        SPAN_COUNT_ONE
    )
    private val itemAdapter by lazy {
        ItemAdapter(gridLayoutManager, Glide.with(this))
    }
    private var viewModel: ImageDataViewModel? = null
    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = gridLayoutManager
        }

        viewModel = ViewModelProviders.of(this@MainActivity).get(ImageDataViewModel::class.java)

        viewModel?.getAll()?.observe(this, Observer<List<ImageData>> { list ->
            itemAdapter.updateDateSet(list)
        })

        viewModel?.getSpanCount()?.observe(this, Observer { spanCount ->
            gridLayoutManager.spanCount = spanCount
            itemAdapter.notifyItemRangeChanged(0, itemAdapter.itemCount)
            switchIcon(menuItem)
        })

        viewModel?.getCurrentState()?.observe(this, Observer { state ->
            errorView.visibility = View.GONE
            recyclerView.visibility = View.GONE
            placeHolderView.visibility = View.GONE
            menuItem?.isVisible = false

            placeHolderView.stopShimmerAnimation()

            when (state) {
                STATE.ERROR -> {
                    errorView.visibility = View.VISIBLE
                }
                STATE.LOADING -> {
                    placeHolderView.visibility = View.VISIBLE
                    placeHolderView.startShimmerAnimation()
                }
                STATE.LOADED -> {
                    recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
                    recyclerView.visibility = View.VISIBLE
                    menuItem?.isVisible = true
                }
            }
        })

        retryButton.setOnClickListener {
            viewModel?.fetchData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menuItem = menu?.findItem(R.id.action_layout)
        switchIcon(item = menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            viewModel?.updateSpanCount(SPAN_COUNT_FOUR)
        } else {
            viewModel?.updateSpanCount(SPAN_COUNT_ONE)
        }
        return true
    }

    private fun switchIcon(item: MenuItem?) {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            item?.icon = resources.getDrawable(R.drawable.ic_grid, null)
        } else {
            item?.icon = resources.getDrawable(R.drawable.ic_list, null)
        }
    }
}