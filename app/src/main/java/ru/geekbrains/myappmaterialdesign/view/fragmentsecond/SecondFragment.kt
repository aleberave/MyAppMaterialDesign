package ru.geekbrains.myappmaterialdesign.view.fragmentsecond

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        if (binding.fabSecondFragment.alpha == 0f) {
            binding.fabSecondFragment.isClickable = false
            binding.fabSecondFragment.isFocusable = false
        }

        binding.fabSecondSF.setOnClickListener { myFabSecondSF() }
        binding.fab1SF.setOnClickListener { navigateTo(EarthFragment()) }
        binding.fab2SF.setOnClickListener { navigateTo(MarsFragment()) }
        binding.fab3SF.setOnClickListener { navigateTo(SystemFragment()) }
    }

    private var isFabOpen = false
    private fun myFabSecondSF() {
        isFabOpen = !isFabOpen
        if (isFabOpen) {
            binding.fab1SF.show()
            binding.fab2SF.show()
            binding.fab3SF.show()
        } else {
            binding.fab1SF.hide()
            binding.fab2SF.hide()
            binding.fab3SF.hide()
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