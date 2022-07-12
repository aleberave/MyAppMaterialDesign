package ru.geekbrains.myappmaterialdesign.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.myappmaterialdesign.databinding.StudyFragmentConstraintBarrierBinding

class StudyConstraintBarrierFragment : Fragment() {

    private var _binding: StudyFragmentConstraintBarrierBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance() = StudyConstraintBarrierFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StudyFragmentConstraintBarrierBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var flag = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.group1.visibility = View.VISIBLE // иногда запаздывает отображение

        binding.group1.setOnClickListener() {
            flag = !flag
            binding.group1.visibility =
                if (binding.group1.visibility == View.VISIBLE) View.VISIBLE else View.GONE // иногда запаздывает отображение
        }

//        View.GONE // делает невидимым элемент, при этом элемент становится по высоте = 0 и по ширене = 0
//        View.INVISIBLE // делает невидимым элемент при этом размеры этого элемента не изменяются
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}