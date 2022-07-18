package ru.geekbrains.myappmaterialdesign.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemEarthBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemHeaderBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemMarsBinding
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerItemSystemBinding
import ru.geekbrains.myappmaterialdesign.recycler.diffutils.Change
import ru.geekbrains.myappmaterialdesign.recycler.diffutils.DiffUtilCallback
import ru.geekbrains.myappmaterialdesign.recycler.diffutils.createCombinedPayload

class RecyclerAdapter(
    private var listData: MutableList<Pair<Data, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem,
    private val callbackMoveUp: MoveUp,
    private val callbackMoveDown: MoveDown
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {

    fun setListDataForDiffUtil(listDataNew: MutableList<Pair<Data, Boolean>>) {
        //TODO расчитываем изменение между парой: старое и новое состояния
        val diff = DiffUtil.calculateDiff(DiffUtilCallback(listData, listDataNew))
        //TODO применить к адаптеру
        diff.dispatchUpdatesTo(this)
        //TODO перезаписать данные
        listData = listDataNew
    }

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

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any> // TODO массив полезной нагрузки
    ) {
        if (payloads.isEmpty()) {
            // TODO если нет различий мужду старым и новым, т.е. payloads пустые,
            //  то вызывается обычный onBindViewHolder(holder: BaseViewHolder, position: Int)
            super.onBindViewHolder(holder, position, payloads)
        } else {
            // TODO если был найдены различия мужду старым и новым

            val createCombinedPayload =
                createCombinedPayload(payloads as List<Change<Pair<Data, Boolean>>>)
            if (createCombinedPayload.oldData.first.name != createCombinedPayload.newData.first.name)
            //TODO если поле name в старом списке отличается от нового, то обновляем не весь элемент,
            // а только одно поле, за счет этого экономия ресурсов
                holder.itemView.findViewById<TextView>(R.id.name).text =
                    createCombinedPayload.newData.first.name

        }
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
        // TODO должен иметь возможность быть изменяемым списоком MutableList<Pair<Data, Boolean>>
        //  вместо обычного списка, в котором нельзя изменять элементы List<Pair<Data, Boolean>>
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

