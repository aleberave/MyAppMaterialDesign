package ru.geekbrains.myappmaterialdesign.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.databinding.StudyFragmentMotionStartBinding
import ru.geekbrains.myappmaterialdesign.view.navigation.MarsFragment

class StudyMotionFragment : Fragment() {

    private var _binding: StudyFragmentMotionStartBinding? = null
    private val binding: StudyFragmentMotionStartBinding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = MarsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StudyFragmentMotionStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}