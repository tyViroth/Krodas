package com.socialism.app.listener

import com.socialism.app.model.Post

interface ClickListener {
    fun onItemClick(post: Post)
}