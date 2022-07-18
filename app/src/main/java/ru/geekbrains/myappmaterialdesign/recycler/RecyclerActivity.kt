package ru.geekbrains.myappmaterialdesign.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: StudyActivityRecyclerBinding
    private val data = arrayListOf(
        Pair(Data("Header", type = TYPE_HEADER), false),
        Pair(Data("Earth", type = TYPE_EARTH), false),
        Pair(Data("Earth", type = TYPE_EARTH), false),
        Pair(Data("Mars", type = TYPE_MARS), false),
        Pair(Data("Earth", type = TYPE_EARTH), false),
        Pair(Data("System", type = TYPE_SYSTEM), false),
        Pair(Data("Earth", type = TYPE_EARTH), false),
        Pair(Data("Mars", type = TYPE_MARS), false),
    )
    lateinit var adapter: RecyclerAdapter

    private val callbackAdd = AddItem {
        when (adapter.getItemViewType(it)) {
            TYPE_EARTH -> {
                data.add(it, Pair(Data("EarthNew", type = TYPE_EARTH), false))
            }
            TYPE_MARS -> {
                data.add(it, Pair(Data("MarsNew", type = TYPE_MARS), false))
            }
            TYPE_SYSTEM -> {
                data.add(it, Pair(Data("SystemNew", type = TYPE_SYSTEM), false))
            }
        }
        adapter.setListDataAdd(data, it)
    }

    private val callbackRemove = RemoveItem {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }

    private val callbackMoveUp = MoveUp {
        if ((it - 1) > 0) { // TODO как-то странно работает
            data.removeAt(it).apply { data.add(it - 1, this) }
        }
    }

    private val callbackMoveDown = MoveDown {
        if ((it + 1) < (data.size - 2)) { // TODO так не работает
            data.removeAt(it).apply { data.add(it + 1, this) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StudyActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter =
            RecyclerAdapter(data, callbackAdd, callbackRemove, callbackMoveUp, callbackMoveDown)
        binding.recyclerView.adapter = adapter

        val lat = 10
        val lon = 30
        val locationOne = lat to lon
        locationOne.first
        locationOne.second
        val locationTwo = Pair(lat, lon)
    }
}