package ru.geekbrains.myappmaterialdesign.view.fragmentsecond

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentSecondBinding
import ru.geekbrains.myappmaterialdesign.view.navigation.EarthPictureFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.MarsPictureFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.SystemPictureFragment

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var isFabOpen = false

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
        if (isFabOpen) isFabOpen = false
        (binding.fabToolBarSF.layoutParams as CoordinatorLayout.LayoutParams).behavior =
            ButtonBehavior(requireContext())

        if (binding.fabToolBarSF.alpha == 0f) {
            binding.fabToolBarSF.isClickable = false
            binding.fabToolBarSF.isFocusable = false
        }

        binding.fabSecondSF.setOnClickListener { myFabSecondSF() }
        binding.fabEarthSF.setOnClickListener { navigateTo(EarthPictureFragment()) }
        binding.fabMarsSF.setOnClickListener { navigateTo(MarsPictureFragment()) }
        binding.fabSystemSF.setOnClickListener { navigateTo(SystemPictureFragment()) }
    }

    private fun myFabSecondSF() {
        isFabOpen = !isFabOpen
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabSecondSF, View.ROTATION, 0f, 360f)
                .setDuration(500L).start()
            binding.fabSecondSF.setImageResource(R.drawable.ic_back_fab)
            binding.fabEarthSF.show()
            binding.fabMarsSF.show()
            binding.fabSystemSF.show()
        } else {
            ObjectAnimator.ofFloat(binding.fabSecondSF, View.ROTATION, 360f, 0f)
                .setDuration(500L).start()
            binding.fabSecondSF.setImageResource(R.drawable.ic_plus_fab)
            binding.fabEarthSF.hide()
            binding.fabMarsSF.hide()
            binding.fabSystemSF.hide()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment).addToBackStack(getString(R.string.empty)).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}