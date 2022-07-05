package ru.geekbrains.myappmaterialdesign.view.navigation.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.geekbrains.myappmaterialdesign.databinding.ActivityApiBinding

class ApiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
//        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.viewPager2.adapter = ViewPagerTwoAdapter(this)
        bindTabLayout()
    }

    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager2,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = when (position) {
                        0 -> "Earth"
                        1 -> "Mars"
                        2 -> "System"
                        else -> "Earth"
                    }
                }
            }
        ).attach()
    }

}