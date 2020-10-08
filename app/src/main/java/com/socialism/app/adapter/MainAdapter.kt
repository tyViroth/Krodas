package com.socialism.app.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.socialism.app.R
import com.socialism.app.listener.ClickListener
import com.socialism.app.model.Post


class MainAdapter(private val clickListener: ClickListener): androidx.recyclerview.widget.ListAdapter<Post, MainAdapter.PostViewHolder>(PostDiffCallback()) {

     private var context: Context? = null
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
          context = parent.context
          val inflater = LayoutInflater.from(parent.context)
          val view = inflater.inflate(R.layout.post_item_view, parent, false)
          return PostViewHolder(view)
     }

     override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
          holder.binding(item = getItem(position), clickListener = clickListener, context = context!!)
     }

     override fun getItemCount(): Int = super.getItemCount()

     override fun getItemId(position: Int): Long = position.toLong()

     class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          private var imageView = itemView.findViewById<ImageView>(R.id.postImageView)
          fun binding(item: Post, clickListener: ClickListener, context: Context) {

               val myOptions = RequestOptions().encodeQuality(30).override(500)
               Glide.with(context)
                    .asBitmap()
                    .thumbnail(0.4f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(myOptions)
                    .load(item.url)
                    .into(imageView)

               itemView.setOnClickListener {
                    clickListener.onItemClick(item)
               }
          }

//          fun getItemDetail(): ItemDetailsLookup.ItemDetails<Parcelable> = object: ItemDetailsLookup.ItemDetails<Parcelable>() {
//               override fun getSelectionKey(): Parcelable? = itemid
//               override fun getPosition(): Int = adapterPosition
//          }
     }

     class PostDiffCallback : DiffUtil.ItemCallback<Post>() {

          override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
               return oldItem.id == newItem.id
          }

          override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
               return oldItem == newItem
          }
     }
}
