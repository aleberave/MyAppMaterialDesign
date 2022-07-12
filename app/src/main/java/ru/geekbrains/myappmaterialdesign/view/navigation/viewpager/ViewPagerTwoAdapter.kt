package ru.geekbrains.myappmaterialdesign.view.navigation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.geekbrains.myappmaterialdesign.utils.keyBundleFragmentVPTA
import ru.geekbrains.myappmaterialdesign.view.navigation.EarthFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.PictureOfTheDayFragmentSecond

class ViewPagerTwoAdapter(fragment: EarthFragment) :
    FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(
        PictureOfTheDayFragmentSecond.newInstance(getBundle()),
        PictureOfTheDayFragmentSecond.newInstance(getBundle()),
        PictureOfTheDayFragmentSecond.newInstance(getBundle())

    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    private var fragmentPosition = 0
    private fun getBundle(): Bundle {
        val bundle = Bundle()
        bundle.putInt(keyBundleFragmentVPTA, fragmentPosition)
        fragmentPosition++
        return bundle
    }
}