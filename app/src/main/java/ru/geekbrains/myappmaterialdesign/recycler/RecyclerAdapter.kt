package ru.geekbrains.myappmaterialdesign.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemEarthBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemHeaderBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemMarsBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemSystemBinding

class RecyclerAdapter(private val listData: List<Data>) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

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
//        when (getItemViewType(position)) {
//            TYPE_EARTH -> {
//                (holder as EarthViewHolder).bind(listData[position])
//            }
//            TYPE_MARS -> {
//                (holder as MarsViewHolder).bind(listData[position])
//            }
//            TYPE_SYSTEM -> {
//                (holder as SystemViewHolder).bind(listData[position])
//            }
//            else -> {
//                (holder as HeaderViewHolder).bind(listData[position])
//            }
//        }
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

    class MarsViewHolder(val binding: StudyActivityRecyclerItemMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class EarthViewHolder(val binding: StudyActivityRecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

    class SystemViewHolder(val binding: StudyActivityRecyclerItemSystemBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: Data) {
            binding.name.text = data.name
        }
    }

}