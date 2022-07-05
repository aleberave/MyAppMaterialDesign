package ru.geekbrains.myappmaterialdesign.view.navigation.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.myappmaterialdesign.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}