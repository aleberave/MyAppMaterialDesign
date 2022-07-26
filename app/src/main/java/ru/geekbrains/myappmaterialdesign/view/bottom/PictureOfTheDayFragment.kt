package ru.geekbrains.myappmaterialdesign.view.bottom

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentPictureOfTheDayBinding
import ru.geekbrains.myappmaterialdesign.utils.pathWikipedia
import ru.geekbrains.myappmaterialdesign.view.settings.SettingsFragment
import ru.geekbrains.myappmaterialdesign.view.fragmenttext.TextFragment
import ru.geekbrains.myappmaterialdesign.view.navigation.BottomBarActivity
import ru.geekbrains.myappmaterialdesign.viewmodel.AppState
import ru.geekbrains.myappmaterialdesign.viewmodel.PictureOfTheDayViewModel
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class PictureOfTheDayFragment : Fragment() {

    private var isRotation = true

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this)[PictureOfTheDayViewModel::class.java] }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_settings, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {

            }
            R.id.action_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack(getString(R.string.empty))
                    .commit()
            }
            android.R.id.home -> {
                requireActivity().let {
                    BottomNavigationDrawerFragment().show(
                        it.supportFragmentManager,
                        bottom_navigation_drawer_fragment
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BottomBarActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
//        getMenu()
        viewModel.getLiveData().observe(
            viewLifecycleOwner
        ) { appState -> renderData(appState) }

        viewModel.sendRequest()

        binding.buttonMain.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, TextFragment.newInstance())
                .addToBackStack(getString(R.string.empty))
                .commit()
        }
        binding.buttonSettings.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .addToBackStack(getString(R.string.empty))
                .commit()
        }

        binding.fab.setOnClickListener {
            isRotation = !isRotation
            if (!isRotation)
                ObjectAnimator.ofFloat(it, View.ROTATION, 0f, 315f).setDuration(1000L).start()
            else
                ObjectAnimator.ofFloat(it, View.ROTATION, 315f, 0f).setDuration(1000L).start()
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
                newMyDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDate.now().toString()
                } else {
                    "${arr[2]}-${arr[1]}-${arr[0]}"
                }
                viewModel.sendRequest(newMyDate)
            }
            yesterday.setOnClickListener {
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
            tda.setOnClickListener {
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
        binding.inputLayoutWikipedia.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                this.data =
                    Uri.parse("$pathWikipedia${binding.inputEditText.text.toString()}")
            })
        }
    }

//    private fun getMenu() {
//        requireActivity().addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.menu_main, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    R.id.action_favorite -> {}
//                    R.id.action_settings -> {
//                        requireActivity().supportFragmentManager.beginTransaction()
//                            .replace(
//                                R.id.nav_host_fragment_content_main,
//                                SettingsFragment.newInstance()
//                            ).addToBackStack(getString(R.string.empty)).commit()
////                        findNavController().navigate(R.id.action_FirstFragment_to_SettingsFragment)
//                    }
//                    android.R.id.home -> {
//                        requireActivity().let {
//                            BottomNavigationDrawerFragment().show(
//                                it.supportFragmentManager,
//                                "tag"
//                            )
//                        }
//                    }
//                    else -> onMenuItemSelected(menuItem)
//                }
//                return true
//            }
//
//        })
//    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                AppState.Error(Throwable(appState.error.message.toString()))
            }
            AppState.Loading -> {
                binding.imageView.visibility = View.GONE
                binding.progressBarPictureOfTheDayFragment.visibility = View.VISIBLE
                Thread { sleep(1000L) }.start()
            }
            is AppState.Success -> {
                binding.progressBarPictureOfTheDayFragment.visibility = View.GONE
                binding.imageView.visibility = View.VISIBLE
                binding.imageView.load(appState.pictureOfTheDayDTO.url) {
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