package com.fstravassos.catgallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fstravassos.catgallery.databinding.ItemPictureBinding

class GalleryAdapter :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var mList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, layout: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPictureBinding>(
            LayoutInflater.from(parent.context), layout, parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_picture
    }

    fun reload(list: List<String>) {
        val diffCallback = PicturesDiffUtilCallback(this.mList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(catPictureUrl: String) {
            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.strokeWidth = 4f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide
                .with(itemView).load(catPictureUrl)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_cloud_load_fail)
                .into(binding.imageViewCatPicture)
        }
    }
}