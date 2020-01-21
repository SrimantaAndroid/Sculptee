package com.sculptee.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacesItemDecoration:RecyclerView.ItemDecoration{
    var gridspace:Int?=null
    constructor( space:Int){
        this.gridspace=space
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left= gridspace!!
        outRect.right=gridspace!!
        outRect.bottom = gridspace!!
        outRect.top = gridspace!!

        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = gridspace!!
        } else {
            outRect.top = 0;
        }*/
    }
}