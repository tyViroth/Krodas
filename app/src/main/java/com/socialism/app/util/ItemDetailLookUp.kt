package com.socialism.app.util

import android.os.Parcelable
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.socialism.app.adapter.MainAdapter
//
//class ItemDetailLookUp(private val recyclerview: RecyclerView): ItemDetailsLookup<Parcelable>() {
//
//    override fun getItemDetails(event: MotionEvent): ItemDetails<Parcelable>? {
//        val view  = recyclerview.findChildViewUnder(event.x, event.y)
//        if(view != null) {
//            return (recyclerview.getChildViewHolder(view) as MainAdapter.PostViewHolder)
//                .getItemDetail()
//        }
//        return null
//    }
//
//}