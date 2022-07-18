package ru.geekbrains.myappmaterialdesign.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: StudyActivityRecyclerBinding
    private val data = arrayListOf(
        Pair(Data(0, "Header", type = TYPE_HEADER), false),
        Pair(Data(1, "Earth", type = TYPE_EARTH), false),
        Pair(Data(2, "Earth", type = TYPE_EARTH), false),
        Pair(Data(3, "Mars", type = TYPE_MARS), false),
        Pair(Data(4, "Earth", type = TYPE_EARTH), false),
        Pair(Data(5, "System", type = TYPE_SYSTEM), false),
        Pair(Data(6, "Earth", type = TYPE_EARTH), false),
        Pair(Data(7, "Mars", type = TYPE_MARS), false),
    )
    lateinit var adapter: RecyclerAdapter
    private var isNewList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StudyActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter =
            RecyclerAdapter(data, callbackAdd, callbackRemove, callbackMoveUp, callbackMoveDown)
        binding.recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallBack(adapter)).attachToRecyclerView(binding.recyclerView)
        binding.recyclerActivityDiffUtilFAB.setOnClickListener {
            //TODO по клику меняет флаг isNewList с false на true и возвращается список
            // в адаптере происходит только ТОЧЕЧНАЯ перересовка текста
            changeAdapterData()
        }
    }

    private val callbackAdd = AddItem {
        when (adapter.getItemViewType(it)) {
            TYPE_EARTH -> {
                data.add(it, Pair(Data(name = "EarthNew", type = TYPE_EARTH), false))
            }
            TYPE_MARS -> {
                data.add(it, Pair(Data(name = "MarsNew", type = TYPE_MARS), false))
            }
            TYPE_SYSTEM -> {
                data.add(it, Pair(Data(name = "SystemNew", type = TYPE_SYSTEM), false))
            }
        }
        adapter.setListDataAdd(data, it)
    }

    private val callbackRemove = RemoveItem {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }

    private val callbackMoveUp = MoveUp {
        data.removeAt(it).apply { data.add(it - 1, this) }
    }

    private val callbackMoveDown = MoveDown {
        data.removeAt(it).apply { data.add(it + 1, this) }
    }

    private fun changeAdapterData() {
        adapter.setListDataForDiffUtil(createItemList(isNewList).map { it }.toMutableList())
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, "Header", type = TYPE_HEADER), false),
                Pair(Data(1, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(2, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(3, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(4, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(5, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(6, "Mars", "", type = TYPE_MARS), false)
            )
            true -> listOf(
                Pair(Data(0, "Header", type = TYPE_HEADER), false),
                Pair(Data(1, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(2, "Jupiter", "", type = TYPE_MARS), false),
                Pair(Data(3, "Mars", "", type = TYPE_MARS), false),
                Pair(Data(4, "Neptune", "", type = TYPE_MARS), false),
                Pair(Data(5, "Saturn", "", type = TYPE_MARS), false),
                Pair(Data(6, "Mars", "", type = TYPE_MARS), false)
            )
        }
    }
}