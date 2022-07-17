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
    private var listData: List<Data>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    fun setListDataAdd(listDataNew: List<Data>, position: Int) {
        listData = listDataNew
        notifyItemInserted(position)
    }

    fun setListDataRemove(listDataNew: List<Data>, position: Int) {
        listData = listDataNew
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
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
        abstract fun bind(data: Data)
    }

    class HeaderViewHolder(val binding: StudyActivityRecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    inner class MarsViewHolder(val binding: StudyActivityRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
//                listData.add(layoutPosition, Data("MarsNew", type = TYPE_MARS))
//                notifyItemInserted(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
//                listData.removeAt(layoutPosition)
//                notifyItemRemoved(layoutPosition)
            }
        }
    }

    inner class EarthViewHolder(val binding: StudyActivityRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
        }
    }

    inner class SystemViewHolder(val binding: StudyActivityRecyclerItemSystemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }
            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }
        }
    }

}