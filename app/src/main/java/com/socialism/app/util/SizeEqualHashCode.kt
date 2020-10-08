package com.socialism.app.util

import com.socialism.app.listener.ISize

class SizeEqualHashCode {
    companion object {

        fun equals(a: ISize, o: Any?): Boolean {
            if (a === o) return true
            if (o == null) return false
            return if (o is ISize) {
                val b = o
                if (a.width() != b.width()) false else a.height() == b.height()
            } else {
                false
            }
        }

        fun hashCode(size: ISize): Int {
            var result = size.width()
            result = 31 * result + size.height()
            return result
        }

    }
}