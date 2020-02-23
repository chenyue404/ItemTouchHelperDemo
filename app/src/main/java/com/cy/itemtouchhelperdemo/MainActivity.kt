package com.cy.itemtouchhelperdemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataArray = arrayOf(
            arrayOf(1, android.R.color.darker_gray),
            arrayOf(2, android.R.color.holo_blue_bright),
            arrayOf(3, android.R.color.holo_blue_dark),
            arrayOf(4, android.R.color.holo_green_light),
            arrayOf(5, android.R.color.holo_green_dark),
            arrayOf(6, android.R.color.holo_orange_light),
            arrayOf(7, android.R.color.holo_orange_dark),
            arrayOf(8, android.R.color.holo_red_dark),
            arrayOf(9, android.R.color.holo_red_light)
        )
        val listAdapter = ListAdapter(dataArray)
        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(listAdapter))
        itemTouchHelper.attachToRecyclerView(rv_list)
        listAdapter.adapterCallback = object : ListAdapter.ListAdapterCallback {
            override fun onLongPress(position: Int, viewHolder: ListAdapter.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
                for (i: Int in 1..dataArray.size) {
                    val holder =
                        rv_list.findViewHolderForAdapterPosition(i) as ListAdapter.ViewHolder
                    holder.iv_delete.visibility = View.VISIBLE
                }
            }
        }
        rv_list.adapter = listAdapter

    }
}
