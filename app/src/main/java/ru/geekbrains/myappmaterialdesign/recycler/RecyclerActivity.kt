package ru.geekbrains.myappmaterialdesign.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.myappmaterialdesign.databinding.StudyActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: StudyActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StudyActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = arrayListOf(
            Data("Header", type = TYPE_HEADER),
            Data("Earth", type = TYPE_EARTH),
            Data("Earth", type = TYPE_EARTH),
            Data("Mars", type = TYPE_MARS),
            Data("Earth", type = TYPE_EARTH),
            Data("System", type = TYPE_SYSTEM),
            Data("Earth", type = TYPE_EARTH),
            Data("Mars", type = TYPE_MARS),
        )

        binding.recyclerView.adapter = RecyclerAdapter(data)
    }
}