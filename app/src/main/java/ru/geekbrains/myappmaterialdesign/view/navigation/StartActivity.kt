package ru.geekbrains.myappmaterialdesign.view.navigation

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.LinearProgressIndicator
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.FragmentStartBinding


class StartActivity : AppCompatActivity() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R)
            setTheme(R.style.MyNewTheme)
        _binding = FragmentStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fullTime = 5000f

//        binding.ivStartFragment.animate().rotation(720f).setDuration(2000L).start()
        ObjectAnimator.ofFloat(binding.ivStartActivity, View.ROTATION, 360f * 3)
            .setDuration(fullTime.toLong())
            .start()

//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, BottomBarActivity::class.java))
//            finish()
//        }, 2000L)

        val progress = findViewById<LinearProgressIndicator>(R.id.progressStartActivity)

        object : CountDownTimer(fullTime.toLong(), 1L) {
            override fun onTick(millisUntilFinished: Long) {
                val process = ((1 - millisUntilFinished / fullTime) * 100).toInt()
                if (progress.progress != process) {
                    progress.progress = process
                }
            }

            override fun onFinish() {
                startActivity(Intent(this@StartActivity, BottomBarActivity::class.java))
                finish()
            }

        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}