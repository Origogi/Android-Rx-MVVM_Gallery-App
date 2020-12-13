package com.origogi.myapplication.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.origogi.myapplication.R
import com.origogi.myapplication.ViewType
import com.origogi.myapplication.model.ImageData

class ItemAdapter(
    private val layoutManager: GridLayoutManager,
    private val requestManager: RequestManager
) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val dataSet = mutableListOf<ImageData>()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
    }

    fun updateDateSet(list: List<ImageData>) {
        dataSet.clear()
        dataSet.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return layoutManager.spanCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = if (viewType == ViewType.LIST.spanCount) {
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        }

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val imageData: ImageData = dataSet[position]
        requestManager.load(imageData.imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.image)
        holder.title.text = imageData.imageTitle

        holder.itemView.setOnClickListener {
            val imageUrl: String = imageData.imageUrl
            val context = it.context

            Intent(context, DetailActivity::class.java).run {
                putExtra("imageUrl", imageUrl)

                val imageView = it.findViewById<View>(R.id.image)
                val pair = Pair(imageView, "image")

                val options: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, pair)

                context.startActivity(this, options.toBundle())
            }
        }
    }

    override fun getItemCount() = dataSet.size
}