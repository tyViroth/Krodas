package com.krodas.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.krodas.app.R
import com.krodas.app.listener.ClickListener
import com.krodas.app.model.Post


class MainAdapter(private val clickListener: ClickListener):
     androidx.recyclerview.widget.ListAdapter<Post, MainAdapter.PostViewHolder>(PostDiffCallback()) {

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
          val inflater = LayoutInflater.from(parent.context)
          val view = inflater.inflate(R.layout.post_item_view, parent, false)
          return PostViewHolder(view)
     }

     override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
          holder.binding(item = getItem(position), clickListener = clickListener)
     }

     override fun getItemCount(): Int = super.getItemCount()

     class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          private var imageView = itemView.findViewById<ImageView>(R.id.postImageView)
          fun binding(item: Post, clickListener: ClickListener) {
               val myOptions = RequestOptions()
                    .encodeQuality(40)

               Glide.with(itemView)
                    .asBitmap()
                    .apply(myOptions)
                    .load(item.url)
                    .into(imageView)

               itemView.setOnClickListener {
                    clickListener.onItemClick(item)
               }
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
