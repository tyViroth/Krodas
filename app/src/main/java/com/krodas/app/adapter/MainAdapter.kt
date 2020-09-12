package com.krodas.app.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.krodas.app.R
import com.krodas.app.listener.ClickListener
import com.krodas.app.model.Post


class MainAdapter(private val clickListener: ClickListener): androidx.recyclerview.widget.ListAdapter<Post, MainAdapter.PostViewHolder>(PostDiffCallback()) {

     var context: Context? = null
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

     class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          private var imageView = itemView.findViewById<ImageView>(R.id.postImageView)
          fun binding(item: Post, clickListener: ClickListener, context: Context) {

               println("Image size ${getDropboxIMGSize(uri = item.url, context = context)}")

               val myOptions = RequestOptions().encodeQuality(40).override(500)
               Glide.with(context)
                    .asBitmap()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(myOptions)
                    .load(item.url)
                    .into(imageView)

               itemView.setOnClickListener {
                    clickListener.onItemClick(item)
               }
          }

          private fun getDropboxIMGSize(uri: Uri, context: Context) : Int{

               val options = BitmapFactory.Options()
               options.apply {
                    inJustDecodeBounds = true
                    inSampleSize = 4
               }

               BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(uri),
                    null,
                    options
               )
               val imageHeight = options.outHeight
               val imageWidth = options.outWidth
               return imageHeight
          }
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
