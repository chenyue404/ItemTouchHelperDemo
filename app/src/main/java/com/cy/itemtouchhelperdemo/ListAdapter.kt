package com.cy.itemtouchhelperdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Eddie on 2020/2/23 0023.
 */
class ListAdapter(private val dataArray: Array<Array<Int>>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>(),
    MyItemTouchHelperCallback.MyTouchHelperListener {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_content = itemView.findViewById<TextView>(R.id.tv_content)
        val iv_delete = itemView.findViewById<View>(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //加一显示第一个拍照item
        return 1 + if (dataArray.isNullOrEmpty()) {
            0
        } else {
            dataArray.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //第一个固定显示拍照
        if (position == 0) {
            holder.tv_content.text = "拍照"
            holder.tv_content.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    android.R.color.black
                )
            )
            return
        }

        val dataPos = position - 1;
        val text = dataArray[dataPos][0].toString()
        val bgColor = ContextCompat.getColor(
            holder.itemView.context,
            dataArray[dataPos][1]
        )
        holder.tv_content.text = text
        holder.tv_content.setBackgroundColor(bgColor)
        holder.itemView.setOnLongClickListener {
            adapterCallback?.onLongPress(position, holder)
            true
        }
    }

    private var startDataPos = -1
    private var endDataPos = -1
    override fun onMove(fromPos: Int, toPos: Int) {
        if (toPos == 0) {
            return
        }
        notifyItemMoved(fromPos, toPos)
        startDataPos = fromPos - 1
    }

    override fun onEnd(endPos: Int) {
        if (startDataPos == endDataPos) {
            return
        }
        endDataPos = endPos - 1;
        //处理数据，脚标归零
        dataArray[startDataPos] = dataArray[endDataPos].also {
            dataArray[endDataPos] = dataArray[startDataPos]
        }
        startDataPos = 0
        endDataPos = 0
    }

    var adapterCallback: ListAdapterCallback? = null

    interface ListAdapterCallback {
        fun onLongPress(position: Int, viewHolder: ViewHolder)
    }
}