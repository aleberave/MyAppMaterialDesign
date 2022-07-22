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
                getNewLayoutFragment(StudyConstraintBarrierFragment.newInstance())
            }
            guidelineSFL.setOnClickListener {
                getNewLayoutFragment(StudyConstraintGuidelineFragment.newInstance())
            }
            coordinatorSFL.setOnClickListener {
                getNewLayoutFragment(StudyCoordinatorFragment.newInstance())
            }
            motionSFL.setOnClickListener {
                getNewLayoutFragment(StudyMotionFragment.newInstance())
            }
        }
    }

    private fun getNewLayoutFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.newLayoutSFL, fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}