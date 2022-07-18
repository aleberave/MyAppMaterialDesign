package ru.geekbrains.myappmaterialdesign.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemEarthBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemHeaderBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemMarsBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemSystemBinding

class RecyclerAdapter(
    private var listData: MutableList<Pair<Data, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem,
    private val callbackMoveUp: MoveUp,
    private val callbackMoveDown: MoveDown
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    fun setListDataAdd(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        listData = listDataNew
        notifyItemInserted(position)
    }

    fun setListDataRemove(listDataNew: MutableList<Pair<Data, Boolean>>, position: Int) {
        listData = listDataNew
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding =
                    StudyActivityRecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding =
                    StudyActivityRecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            TYPE_SYSTEM -> {
                val binding =
                    StudyActivityRecyclerItemSystemBinding.inflate(LayoutInflater.from(parent.context))
                SystemViewHolder(binding)
            }
            else -> {
                val binding =
                    StudyActivityRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    private fun getMoveItemUp(position: Int) {
        if (position - 1 > 0) {
            callbackMoveUp.moveUp(position)
            notifyItemMoved(position, position - 1)
        }
    }

    private fun getMoveItemDown(position: Int) {
        if (listData.size > position + 1) {
            callbackMoveDown.moveDown(position)
            notifyItemMoved(position, position + 1)
        }
    }

    private fun getDescription(position: Int) {
        listData[position] = listData[position].let { it.first to !it.second }
        notifyItemChanged(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (toPosition > 0) {
            listData.removeAt(fromPosition).apply {
                listData.add(toPosition, this)
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }

    abstract class BaseViewHolder(val view: View) :
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        abstract fun bind(data: Pair<Data, Boolean>)
        override fun onItemSelected() {
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        }

        override fun onItemClear() {
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.purple))
        }
    }

    class HeaderViewHolder(val binding: StudyActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.name.text = data.first.name
        }
    }

    inner class MarsViewHolder(val binding: StudyActivityRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {
            with(binding) {
                name.text = data.first.name
                addItemImageView.setOnClickListener { callbackAdd.add(layoutPosition) }
                removeItemImageView.setOnClickListener { callbackRemove.remove(layoutPosition) }
                moveItemUp.setOnClickListener { getMoveItemUp(layoutPosition) }
                moveItemDown.setOnClickListener { getMoveItemDown(layoutPosition) }

                marsImageView.setOnClickListener { getDescription(layoutPosition) }
                marsDescriptionTextView.visibility =
                    if (listData[layoutPosition].second) View.VISIBLE else View.GONE
            }
        }
    }

    inner class EarthViewHolder(val binding: StudyActivityRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            with(binding) {
                name.text = data.first.name
                addItemImageView.setOnClickListener { callbackAdd.add(layoutPosition) }
                removeItemImageView.setOnClickListener { callbackRemove.remove(layoutPosition) }
                moveItemUp.setOnClickListener { getMoveItemUp(layoutPosition) }
                moveItemDown.setOnClickListener { getMoveItemDown(layoutPosition) }

                earthImageView.setOnClickListener { getDescription(layoutPosition) }
                earthDescriptionTextView.visibility =
                    if (listData[layoutPosition].second) View.VISIBLE else View.GONE
            }
        }
    }

    inner class SystemViewHolder(val binding: StudyActivityRecyclerItemSystemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            with(binding) {
                name.text = data.first.name
                addItemImageView.setOnClickListener { callbackAdd.add(layoutPosition) }
                removeItemImageView.setOnClickListener { callbackRemove.remove(layoutPosition) }
                moveItemUp.setOnClickListener { getMoveItemUp(layoutPosition) }
                moveItemDown.setOnClickListener { getMoveItemDown(layoutPosition) }

                systemImageView.setOnClickListener { getDescription(layoutPosition) }
                systemDescriptionTextView.visibility =
                    if (listData[layoutPosition].second) View.VISIBLE else View.GONE
            }
        }
    }

}

