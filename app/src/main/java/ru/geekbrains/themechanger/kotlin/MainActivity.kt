package ru.geekbrains.themechanger.kotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.themechanger.R
import ru.geekbrains.themechanger.databinding.ActivityMainBinding

const val ThemeOne = 1
const val ThemeSecond = 2

class MainActivity : AppCompatActivity(), View.OnClickListener {



    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // интересный факт, если не вынести init() в onResume() то кнопки radioGroup не будут менять свой статус check
    override fun onResume() {
        super.onResume()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_first,FirstFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_second,SecondFragment.newInstance()).commit()
        init()
    }
    private fun init() {
        binding.btnThemeOne.setOnClickListener(this)
        binding.btnThemeSecond.setOnClickListener(this)
        when (getCurrentTheme()) {
            1 ->  binding.radioGroup.check(R.id.btnThemeOne)
            2 ->  binding.radioGroup.check(R.id.btnThemeSecond)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnThemeOne -> setCurrentTheme(ThemeOne)
            R.id.btnThemeSecond -> setCurrentTheme(ThemeSecond)
        }
        recreate()
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeOne -> R.style.ThemeOne
            ThemeSecond -> R.style.ThemeSecond
            else -> 0
        }
    }
}