package com.origogi.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.origogi.myapplication.log.Logger
import com.origogi.myapplication.model.ImageData
import com.origogi.myapplication.viewmodel.ImageDataViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val gridLayoutManager = GridLayoutManager(this, TYPE_LIST)
    private val itemAdapter = ItemAdapter(gridLayoutManager, this)
    private var viewModel : ImageDataViewModel? = null
    private var menuItem : MenuItem? = null

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

        viewModel?.getSpanCount()?.observe(this, Observer { spanCount->
            gridLayoutManager.spanCount = spanCount
            itemAdapter.notifyItemRangeChanged(0, itemAdapter.itemCount)
            switchIcon(menuItem)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);

        menuItem = menu?.findItem(R.id.action_layout)
        return true; }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (gridLayoutManager.spanCount == TYPE_LIST) {
            viewModel?.updateSpanCount(TYPE_GRID)
        } else {
            viewModel?.updateSpanCount(TYPE_LIST)
        }
        return true;
    }

    private fun switchIcon(item: MenuItem?) {
        if (gridLayoutManager.spanCount == TYPE_LIST) {
            item?.icon = resources.getDrawable(R.drawable.ic_grid)
        } else {
            item?.icon = resources.getDrawable(R.drawable.ic_list)

        }
    }
}