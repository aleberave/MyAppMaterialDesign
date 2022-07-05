package ru.geekbrains.myappmaterialdesign.view.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.geekbrains.myappmaterialdesign.view.navigation.EarthFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.MarsFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.SystemFragment

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Earth"
            1 -> "Mars"
            2 -> "System"
            else -> "Earth"
        }
    }
}
