package com.origogi.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.origogi.myapplication.*
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.viewmodel.ImageDataViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_placeholder.*
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {
    private val gridLayoutManager = GridLayoutManager(
        this,
        ViewType.LIST.spanCount
    )
    private val itemAdapter by lazy {
        ItemAdapter(gridLayoutManager, Glide.with(this))
    }
    private var viewModel: ImageDataViewModel? = null
    private var menuItem: MenuItem? = null

    override fun onPause() {
        super.onPause()
        Log.d("TEST","Main onPause()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TEST","Main onResume()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TEST","Main onStart()")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("TEST","Main onRestart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TEST","Main onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TEST","Main onDestroy()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TEST","Main onCreate()")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = gridLayoutManager
        }

        viewModel = ViewModelProviders.of(this@MainActivity).get(ImageDataViewModel::class.java)

        viewModel?.getImageDataList()?.observe(this, Observer<List<ImageData>> { list ->
            itemAdapter.updateDateSet(list)
        })

        viewModel?.getViewType()?.observe(this, Observer { viewType ->
            gridLayoutManager.spanCount = viewType.spanCount
            itemAdapter.notifyItemRangeChanged(0, itemAdapter.itemCount)
            switchIcon(menuItem)
        })

        viewModel?.getAppState()?.observe(this, Observer { state ->
            errorView.visibility = View.GONE
            recyclerView.visibility = View.GONE
            loadingView.visibility = View.GONE
            menuItem?.isVisible = false

            loadingView.stopShimmerAnimation()

            when (state) {
                AppState.ERROR -> {
                    errorView.visibility = View.VISIBLE
                }
                AppState.LOADING -> {
                    loadingView.visibility = View.VISIBLE
                    loadingView.startShimmerAnimation()
                }
                AppState.LOADED -> {
                    recyclerView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
                    recyclerView.visibility = View.VISIBLE
                    menuItem?.isVisible = true
                }
                else -> throw AssertionError() // impossible case
            }
        })

        retryButton.setOnClickListener {
            viewModel?.fetchData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        menuItem = menu?.findItem(R.id.action_layout)?.apply {
            isVisible = false

            if (viewModel?.getAppState()?.value == AppState.LOADED) {
                isVisible = true
            }
        }
        switchIcon(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (gridLayoutManager.spanCount == ViewType.LIST.spanCount) {
            viewModel?.updateViewType(ViewType.GRID)
        } else {
            viewModel?.updateViewType(ViewType.LIST)
        }
        return true
    }

    private fun switchIcon(item: MenuItem?) {
        if (gridLayoutManager.spanCount == ViewType.LIST.spanCount) {
            item?.icon = resources.getDrawable(R.drawable.ic_grid, null)
        } else {
            item?.icon = resources.getDrawable(R.drawable.ic_list, null)
        }
    }
}