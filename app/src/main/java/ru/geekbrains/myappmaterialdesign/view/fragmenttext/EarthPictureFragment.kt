package ru.geekbrains.myappmaterialdesign.view.fragmenttext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.databinding.FragmentEarthPictureBinding

class EarthPictureFragment : Fragment() {

    private var _binding: FragmentEarthPictureBinding? = null
    private val binding: FragmentEarthPictureBinding
        get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = MarsPictureFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthPictureBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}