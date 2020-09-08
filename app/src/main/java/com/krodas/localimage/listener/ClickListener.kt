package com.krodas.localimage.listener

import com.krodas.localimage.model.Post

interface ClickListener {
    fun onItemClick(post: Post)
}