package ru.geekbrains.myappmaterialdesign

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import ru.geekbrains.myappmaterialdesign.databinding.FragmentPictureOfTheDayBinding
import ru.geekbrains.myappmaterialdesign.utils.pathWikipedia
import ru.geekbrains.myappmaterialdesign.viewmodel.AppState
import ru.geekbrains.myappmaterialdesign.viewmodel.PictureOfTheDayViewModel
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[PictureOfTheDayViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { appState -> renderData(appState) }

        viewModel.sendRequest()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        getWikipedia()
        getChipById()
    }

    private fun getChipById() {
        val currentDate: String =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        val arr = arrayOf(
            currentDate.substring(0, 2),
            currentDate.substring(3, 5),
            currentDate.substring(6, 10)
        )
        var newMyDate: String
        with(binding) {
            today.setOnClickListener {
                newMyDate = "${arr[2]}-${arr[1]}-${arr[0]}"
                viewModel.sendRequest(newMyDate)
            }
            yesterday.setOnClickListener {
                val number: Int = arr[0].toInt() - 1
                newMyDate = if (number > 0) {
                    "${arr[2]}-${arr[1]}-$number"
                } else {
                    "${arr[2]}-${arr[1]}-${arr[0]}"
                }
                viewModel.sendRequest(newMyDate)
            }
            tda.setOnClickListener {
                val number: Int = arr[0].toInt() - 2
                newMyDate = if (number > 0) {
                    "${arr[2]}-${arr[1]}-$number"
                } else {
                    "${arr[2]}-${arr[1]}-${arr[0]}"
                }
                viewModel.sendRequest(newMyDate)
            }
        }
    }

    private fun getWikipedia() {
        binding.inputLayoutWikipedia.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                this.data =
                    Uri.parse("$pathWikipedia${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                AppState.Error(Throwable(appState.error.message.toString()))
            }
            AppState.Loading -> {
                binding.constraintPictureOfTheDayFragment.visibility = View.GONE
                binding.progressBarPictureOfTheDayFragment.visibility = View.VISIBLE
                Thread { sleep(1000L) }.start()
            }
            is AppState.Success -> {
                binding.progressBarPictureOfTheDayFragment.visibility = View.GONE
                binding.constraintPictureOfTheDayFragment.visibility = View.VISIBLE
                binding.imageView.load(appState.pictureOfTheDayDTO.url) {
                    //TODO HW настроить загрузку изображения: error() placeholder()
                    error(R.drawable.ic_error)
                    placeholder(R.drawable.ic_insights)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}