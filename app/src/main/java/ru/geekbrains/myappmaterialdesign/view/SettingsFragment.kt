package ru.geekbrains.myappmaterialdesign.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context: Context =
            ContextThemeWrapper(requireActivity(), getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonMain.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host_fragment_content_main, PictureOfTheDayFragment.newInstance())
//                .commit()
//        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                getTabSelected(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                getTabSelected(tab)
            }

        })
    }

    private fun getTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                setCurrentTheme(ThemeRed)
                requireActivity().recreate()
            }
            1 -> {
                setCurrentTheme(ThemeBlue)
                requireActivity().recreate()
            }
            2 -> {
                setCurrentTheme(ThemeGreen)
                requireActivity().recreate()
            }
        }
    }

    private fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                KEY_SP,
                AppCompatActivity.MODE_PRIVATE
            )
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_SP_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(
                KEY_SP,
                AppCompatActivity.MODE_PRIVATE
            )
        return sharedPreferences.getInt(KEY_SP_CURRENT_THEME, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeRed -> R.style.MyRedTheme
            ThemeBlue -> R.style.MyBlueTheme
            ThemeGreen -> R.style.MyGreenTheme
            else -> R.style.MyRedTheme
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}