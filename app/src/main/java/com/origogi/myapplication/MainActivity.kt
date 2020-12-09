package com.origogi.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private val itemAdapter = ItemAdapter(gridLayoutManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            adapter = itemAdapter
            layoutManager = gridLayoutManager
        }

        val viewModel = ViewModelProviders.of(this@MainActivity).get(ImageDataViewModel::class.java)

        viewModel.getAll().observe(this, Observer<List<ImageData>> { list ->
            itemAdapter.updateDateSet(list)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);

        return true; }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        switchLayout()
        switchIcon(item)

        return true;
    }

    private fun switchLayout() {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            gridLayoutManager.spanCount = SPAN_COUNT_THREE
        } else {
            gridLayoutManager.spanCount = SPAN_COUNT_ONE
        }
        itemAdapter.notifyItemRangeChanged(0, itemAdapter.itemCount)
    }

    fun switchIcon(item: MenuItem) {
        if (gridLayoutManager.spanCount == SPAN_COUNT_ONE) {
            item.icon = resources.getDrawable(R.drawable.ic_grid)
        } else {
            item.icon = resources.getDrawable(R.drawable.ic_list)

        }
    }
}