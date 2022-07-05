package ru.geekbrains.myappmaterialdesign.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        binding.buttonMain.setOnClickListener {
            findNavController().navigate(R.id.action_SettingsFragment_to_FirstFragment)
        }
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
                Toast.makeText(
                    requireContext(), tab.position.toString(), Toast.LENGTH_SHORT
                ).show()
                setCurrentTheme(ThemeRed)
                requireActivity().onBackPressed()
            }
            1 -> {
                Toast.makeText(
                    requireContext(), tab.position.toString(), Toast.LENGTH_SHORT
                ).show()
                setCurrentTheme(ThemeBlue)
                requireActivity().onBackPressed()
            }
            2 -> {
                Toast.makeText(
                    requireContext(), tab.position.toString(), Toast.LENGTH_SHORT
                ).show()
                setCurrentTheme(ThemeGreen)
                requireActivity().onBackPressed()
            }
        }
    }

    fun setCurrentTheme(currentTheme: Int) {
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
            else -> 0
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}