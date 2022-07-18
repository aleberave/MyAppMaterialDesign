package ru.geekbrains.myappmaterialdesign.recycler

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallBack(private val callback: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {

    // TODO в какие стороны двигать
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipe = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(drag, swipe)
    }

    // TODO что делать при передвижении
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        callback.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    // TODO что делать при смахивание
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        callback.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (viewHolder is RecyclerAdapter.BaseViewHolder) {
            viewHolder.onItemSelected()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is RecyclerAdapter.BaseViewHolder) {
            viewHolder.onItemClear()
        }
    }
}