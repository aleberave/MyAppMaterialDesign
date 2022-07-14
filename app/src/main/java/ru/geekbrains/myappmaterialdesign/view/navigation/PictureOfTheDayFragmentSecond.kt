package ru.geekbrains.myappmaterialdesign.view.navigation

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentPictureOfTheDaySecondBinding
import ru.geekbrains.myappmaterialdesign.utils.keyBundleFragmentVPTA
import ru.geekbrains.myappmaterialdesign.utils.pathWikipedia
import ru.geekbrains.myappmaterialdesign.viewmodel.AppState
import ru.geekbrains.myappmaterialdesign.viewmodel.PictureOfTheDayViewModel
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PictureOfTheDayFragmentSecond : Fragment() {

    private var _binding: FragmentPictureOfTheDaySecondBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[PictureOfTheDayViewModel::class.java] }

    private var isImageClick = false

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): Fragment {
            val fragmentSecond = PictureOfTheDayFragmentSecond()
            fragmentSecond.arguments = bundle
            return fragmentSecond
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDaySecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { appState ->
            renderData(appState)
        }

        getWikipedia()
        val intMyPosition = arguments?.getInt(keyBundleFragmentVPTA)
        getDay(intMyPosition!!)

        getImageListener()
    }

    private fun getImageListener() {
        binding.imageViewSecond.setOnClickListener {
            isImageClick = !isImageClick
            val transitionSet = TransitionSet()
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000L
            val changeImageTransform = ChangeImageTransform()
            changeImageTransform.duration = 1000L
            transitionSet.addTransition(changeBounds)
            transitionSet.addTransition(changeImageTransform)
            TransitionManager.beginDelayedTransition(
                binding.constraintPictureOfTheDayFragmentSecond,
                transitionSet
            )

            val params = it.layoutParams as ConstraintLayout.LayoutParams
            if (isImageClick) {
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
                (it as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                (it as ImageView).scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
            it.layoutParams = params
        }
    }

    private fun getDay(position: Int) {
        val currentDate: String =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        val arr = arrayOf(
            currentDate.substring(0, 2),
            currentDate.substring(3, 5),
            currentDate.substring(6, 10)
        )
        val newMyDate: String

        when (position) {
            0 -> {
                newMyDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.now().toString()
                } else {
                    "${arr[2]}-${arr[1]}-${arr[0]}"
                }
                viewModel.sendRequest(newMyDate)
            }
            1 -> {
                newMyDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.now().minusDays(1).toString()
                } else {
                    val number: Int = arr[0].toInt() - 1
                    if (number > 0) {
                        "${arr[2]}-${arr[1]}-$number"
                    } else {
                        "${arr[2]}-${arr[1]}-${arr[0]}"
                    }
                }
                viewModel.sendRequest(newMyDate)
            }
            2 -> {
                newMyDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.now().minusDays(2).toString()
                } else {
                    val number: Int = arr[0].toInt() - 2
                    if (number > 0) {
                        "${arr[2]}-${arr[1]}-$number"
                    } else {
                        "${arr[2]}-${arr[1]}-${arr[0]}"
                    }
                }
                viewModel.sendRequest(newMyDate)
            }
        }
    }

    private fun getWikipedia() {
        binding.inputLayoutWikipediaSecond.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                this.data =
                    Uri.parse("$pathWikipedia${binding.inputEditTextSecond.text.toString()}")
            })
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                AppState.Error(Throwable(appState.error.message.toString()))
            }
            AppState.Loading -> {
                binding.constraintPictureOfTheDayFragmentSecond.visibility = View.GONE
                binding.progressBarPictureOfTheDayFragmentSecond.visibility = View.VISIBLE
                Thread { sleep(10L) }.start()
            }
            is AppState.Success -> {
                binding.progressBarPictureOfTheDayFragmentSecond.visibility = View.GONE
                binding.constraintPictureOfTheDayFragmentSecond.visibility = View.VISIBLE
                binding.imageViewSecond.load(appState.pictureOfTheDayDTO.url) {
                    error(R.drawable.ic_error)
                    placeholder(R.drawable.ic_insights)
                }
                binding.bottomSheetDescriptionHeader.text = appState.pictureOfTheDayDTO.title
                binding.bottomSheetDescription.text = appState.pictureOfTheDayDTO.explanation
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}