package com.socialism.app.repository

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.socialism.app.model.Post


class LocalService {

    suspend fun localFile(activity: Activity): ArrayList<Post> {
        val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val columnIndexID: Int
        val listOfAllImages: ArrayList<Post> = ArrayList()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        var imageId: Long

        cursor = activity.contentResolver.query(uriExternal, projection, null, null, null)
        if (cursor != null) {
            columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                imageId = cursor.getLong(columnIndexID)
                val uriImage = Uri.withAppendedPath(uriExternal, "" + imageId)
                listOfAllImages.add(Post(id = imageId, url = uriImage))
            }
            cursor.close()
        }
        return listOfAllImages
    }
}
