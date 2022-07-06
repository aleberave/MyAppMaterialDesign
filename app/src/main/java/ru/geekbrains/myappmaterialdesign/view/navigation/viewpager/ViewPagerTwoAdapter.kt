package ru.geekbrains.myappmaterialdesign.view.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.geekbrains.myappmaterialdesign.view.navigation.EarthFragment
import java.util.*

class ViewPagerTwoAdapter(fragment: EarthFragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(
        PictureOfTheDayFragmentSecond(0),
        PictureOfTheDayFragmentSecond(1),
        PictureOfTheDayFragmentSecond(2)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}