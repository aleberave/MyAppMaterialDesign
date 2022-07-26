package ru.geekbrains.myappmaterialdesign.view.layouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentLayoutsBinding

class LayoutsFragment : Fragment() {

    private var _binding: FragmentLayoutsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLayoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getChipsLayouts()
    }

    private fun getChipsLayouts() {

        with(binding) {
            barrierSFL.setOnClickListener {
                navigateTo(StudyConstraintBarrierFragment.newInstance())
            }
            guidelineSFL.setOnClickListener {
                navigateTo(StudyConstraintGuidelineFragment.newInstance())
            }
            coordinatorSFL.setOnClickListener {
                navigateTo(StudyCoordinatorFragment.newInstance())
            }
            motionSFL.setOnClickListener {
                navigateTo(StudyMotionFragment.newInstance())
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            .add(R.id.newLayoutSFL, fragment).addToBackStack(getString(R.string.empty)).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}