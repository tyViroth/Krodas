package com.krodas.app.listener

import com.krodas.app.model.Post

interface ClickListener {
    fun onItemClick(post: Post)
}