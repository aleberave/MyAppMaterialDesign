package ru.geekbrains.myappmaterialdesign.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.databinding.FragmentSystemBinding

class SystemPictureFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    val binding: FragmentSystemBinding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = SystemPictureFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
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