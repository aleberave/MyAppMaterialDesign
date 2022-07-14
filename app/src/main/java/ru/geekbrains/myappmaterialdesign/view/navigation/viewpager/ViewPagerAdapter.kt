package ru.geekbrains.myappmaterialdesign.view.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.geekbrains.myappmaterialdesign.view.navigation.CosmosFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.MarsPictureFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.SystemPictureFragment

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

//TODO
// Чтобы  вызвать в активити:
//        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
//        binding.tabLayout.setupWithViewPager(binding.viewPager)

    private val fragments = arrayOf(CosmosFragment(), MarsPictureFragment(), SystemPictureFragment())

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
