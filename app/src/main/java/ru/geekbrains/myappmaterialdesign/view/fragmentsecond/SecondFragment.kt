package ru.geekbrains.myappmaterialdesign.view.fragmentsecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentSecondBinding
import ru.geekbrains.myappmaterialdesign.view.navigation.EarthFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.MarsFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.SystemFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (binding.fabSecondFragment.layoutParams as CoordinatorLayout.LayoutParams).behavior =
            ButtonBehavior(requireContext())

        if (binding.fabSecondFragment.alpha == 0f) {
            binding.fabSecondFragment.isClickable = false
            binding.fabSecondFragment.isFocusable = false
        }

        binding.fabSecondSF.setOnClickListener { myFabSecondSF() }
        binding.fabEarthSF.setOnClickListener { navigateTo(EarthFragment()) }
        binding.fabMarsSF.setOnClickListener { navigateTo(MarsFragment()) }
        binding.fabSystemSF.setOnClickListener { navigateTo(SystemFragment()) }
    }

    private var isFabOpen = false
    private fun myFabSecondSF() {
        isFabOpen = !isFabOpen
        if (isFabOpen) {
            binding.fabSecondSF.setImageResource(R.drawable.ic_back_fab)
            binding.fabEarthSF.show()
            binding.fabMarsSF.show()
            binding.fabSystemSF.show()
        } else {
            binding.fabSecondSF.setImageResource(R.drawable.ic_plus_fab)
            binding.fabEarthSF.hide()
            binding.fabMarsSF.hide()
            binding.fabSystemSF.hide()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}