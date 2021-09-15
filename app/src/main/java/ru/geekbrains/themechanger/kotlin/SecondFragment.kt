package ru.geekbrains.themechanger.kotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import ru.geekbrains.themechanger.R
import ru.geekbrains.themechanger.databinding.FragmentSecondBinding


class SecondFragment : Fragment(), View.OnClickListener {

    private val KEY_SP_LOCAL = "sp_local"
    private val KEY_CURRENT_THEME_LOCAL = "current_theme_local"

    private lateinit var parentActivity: MainActivity // 1 способ получить родительскую активити
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity) // 1 способ получить родительскую активити

        parentActivity = activity as MainActivity // воторой способ
        parentActivity =
            requireActivity() as MainActivity // третий способ( на самом деле то же самое все, просто со встроенной проверкой актвити на null)
    }

    private var _binding: FragmentSecondBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context: Context = ContextThemeWrapper(activity, getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSecondBinding.inflate(localInflater) // здесь inflater созданный нами на месте
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnThemeOneLocal.setOnClickListener(this)
        binding.btnThemeSecondLocal.setOnClickListener(this)
        when (getCurrentThemeLocal()) {
            1 -> binding.radioGroupLocal.check(R.id.btnThemeOneLocal)
            2 -> binding.radioGroupLocal.check(R.id.btnThemeSecondLocal)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnThemeOneLocal -> {
                setCurrentThemeLocal(ThemeOne)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_second, SecondFragment.newInstance())
                    .commit();// применяем для всей активити и для всех дочерних фрагментов
            }

            R.id.btnThemeSecondLocal -> {
                setCurrentThemeLocal(ThemeSecond)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container_second, SecondFragment.newInstance())
                    .commit();// применяем для всей активити и для всех дочерних фрагментов
            }
        }

    }

    companion object {
        fun newInstance() = SecondFragment()
    }

    private fun setCurrentThemeLocal(currentTheme: Int) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME_LOCAL, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeOne -> R.style.ThemeOne
            ThemeSecond -> R.style.ThemeSecond
            else -> 0
        }
    }
}