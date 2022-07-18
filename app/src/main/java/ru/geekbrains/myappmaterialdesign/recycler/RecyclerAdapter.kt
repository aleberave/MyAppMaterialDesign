package ru.geekbrains.myappmaterialdesign.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemEarthBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemHeaderBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemMarsBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemSystemBinding

class RecyclerAdapter(
    private var listData: MutableList<Pair<Data, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem,
    val callbackMoveUp: MoveUp,
    val callbackMoveDown: MoveDown
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

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

    abstract class BaseViewHolder(val view: View) :
        RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Pair<Data, Boolean>)
    }

    class HeaderViewHolder(val binding: StudyActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.name.text = data.first.name
        }
    }

    inner class MarsViewHolder(val binding: StudyActivityRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.name.text = data.first.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
            binding.moveItemUp.setOnClickListener {
                callbackMoveUp.moveUp(layoutPosition)
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
            binding.moveItemDown.setOnClickListener {
                callbackMoveDown.moveDown(layoutPosition)
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }

            binding.marsImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let { it.first to !it.second }
                notifyItemChanged(layoutPosition)
            }
            binding.marsDescriptionTextView.visibility =
                if (listData[layoutPosition].second) View.VISIBLE else View.GONE
        }
    }

    inner class EarthViewHolder(val binding: StudyActivityRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.name.text = data.first.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
            binding.moveItemUp.setOnClickListener {
                callbackMoveUp.moveUp(layoutPosition)
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
            binding.moveItemDown.setOnClickListener {
                callbackMoveDown.moveDown(layoutPosition)
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }

            binding.earthImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let { it.first to !it.second }
                notifyItemChanged(layoutPosition)
            }
            binding.earthDescriptionTextView.visibility =
                if (listData[layoutPosition].second) View.VISIBLE else View.GONE
        }
    }

    inner class SystemViewHolder(val binding: StudyActivityRecyclerItemSystemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Pair<Data, Boolean>) {
            binding.name.text = data.first.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
            binding.moveItemUp.setOnClickListener {
                callbackMoveUp.moveUp(layoutPosition)
                notifyItemMoved(layoutPosition, layoutPosition - 1)
            }
            binding.moveItemDown.setOnClickListener {
                callbackMoveDown.moveDown(layoutPosition)
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
            binding.systemImageView.setOnClickListener {
                listData[layoutPosition] = listData[layoutPosition].let { it.first to !it.second }
                notifyItemChanged(layoutPosition)
            }
            binding.systemDescriptionTextView.visibility =
                if (listData[layoutPosition].second) View.VISIBLE else View.GONE
        }
    }

}