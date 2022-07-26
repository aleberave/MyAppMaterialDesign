package ru.geekbrains.myappmaterialdesign.view.navigation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.ActivityBottomBarBinding
import ru.geekbrains.myappmaterialdesign.view.bottom.PictureOfTheDayFragment
import ru.geekbrains.myappmaterialdesign.view.fragmenttext.TextFragment
import ru.geekbrains.myappmaterialdesign.view.layouts.LayoutsFragment
import ru.geekbrains.myappmaterialdesign.view.settings.SettingsFragment

class BottomBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_view_cosmos_every_day -> {
                    navigateTo(CosmosFragment());true
                }
                R.id.action_view_study_layout -> {
                    // TODO у меня нет идей где применить Guideline и Barr ier, Motion (Chip/TabLayout?!)
//                    navigateTo(StudyConstraintGuidelineFragment());false
//                    navigateTo(StudyConstraintBarrierFragment());false
//                    navigateTo(StudyMotionFragment());false
                    navigateTo(LayoutsFragment());false
                }
                R.id.action_view_system -> {
                    navigateTo(PictureOfTheDayFragment())
                    binding.bottomNavigationView.removeBadge(R.id.action_view_system)
                    true
                }
                R.id.action_view_text_fragment -> {
                    navigateTo(TextFragment());true
                }
                R.id.action_view_settings -> {
                    navigateTo(SettingsFragment());false
                }
                else -> true
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.action_view_cosmos_every_day
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.action_view_system)
        badge.number = 1000
        badge.maxCharacterCount = 5
        badge.badgeGravity = BadgeDrawable.BOTTOM_END
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}