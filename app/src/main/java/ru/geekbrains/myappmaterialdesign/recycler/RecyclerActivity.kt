package ru.geekbrains.myappmaterialdesign.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: StudyActivityRecyclerBinding
    private val data = arrayListOf(
        Data("Header", type = TYPE_HEADER),
        Data("Earth", type = TYPE_EARTH),
        Data("Earth", type = TYPE_EARTH),
        Data("Mars", type = TYPE_MARS),
        Data("Earth", type = TYPE_EARTH),
        Data("System", type = TYPE_SYSTEM),
        Data("Earth", type = TYPE_EARTH),
        Data("Mars", type = TYPE_MARS),
    )
    lateinit var adapter: RecyclerAdapter

    private val callbackAdd = AddItem {
        when (adapter.getItemViewType(it)) {
            TYPE_EARTH -> {
                data.add(it, Data("EarthNew", type = TYPE_EARTH))
            }
            TYPE_MARS -> {
                data.add(it, Data("MarsNew", type = TYPE_MARS))
            }
            TYPE_SYSTEM -> {
                data.add(it, Data("SystemNew", type = TYPE_SYSTEM))
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
        if ((it + 1) < (data.size-2)) { // TODO так не работает
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
    }
}