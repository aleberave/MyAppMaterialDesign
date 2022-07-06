package ru.geekbrains.myappmaterialdesign.view

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import ru.geekbrains.myappmaterialdesign.R
import ru.geekbrains.myappmaterialdesign.databinding.ActivityMainBinding


const val ThemeRed = 1
const val ThemeBlue = 2
const val ThemeGreen = 3

const val KEY_SP = "sp"
const val KEY_SP_CURRENT_THEME = "current_theme"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        if (getCurrentTheme() > 0) {
            setTheme(getRealStyle(getCurrentTheme()))
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host_fragment_content_main, PictureOfTheDayFragment.newInstance())
                .commit()
        }
        if (savedInstanceState != null && theme.toString() != getRealStyle(getCurrentTheme()).toString()) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_content_main,
                    PictureOfTheDayFragment.newInstance()
                )
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite_activity_main -> {
                true
            }
            R.id.action_settings_activity_main -> {
                supportFragmentManager.beginTransaction()
                    .remove(PictureOfTheDayFragment())
                    .replace(R.id.nav_host_fragment_content_main, SettingsFragment.newInstance())
                    .addToBackStack(getString(R.string.empty))
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
            }
        }
    }

    private fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_SP_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeRed -> R.style.MyRedTheme
            ThemeBlue -> R.style.MyBlueTheme
            ThemeGreen -> R.style.MyGreenTheme
            else -> 0
        }
    }

    override fun onResume() {
        super.onResume()
        setTheme(getRealStyle(getCurrentTheme()))
    }

}