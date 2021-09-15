package ru.geekbrains.themechanger.kotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.themechanger.R
import ru.geekbrains.themechanger.databinding.FragmentFirstBinding


class FirstFragment : Fragment(), View.OnClickListener {

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

    private var _binding: FragmentFirstBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater) // здесь inflater родительской активити
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnThemeOne.setOnClickListener(this)
        binding.btnThemeSecond.setOnClickListener(this)

        when (parentActivity.getCurrentTheme()) {
            1 -> binding.radioGroup.check(R.id.btnThemeOne)
            2 -> binding.radioGroup.check(R.id.btnThemeSecond)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnThemeOne -> {
                parentActivity.setCurrentTheme(ThemeOne)
                parentActivity.recreate() // применяем для всей активити и для всех дочерних фрагментов
            }
            R.id.btnThemeSecond -> {
                parentActivity.setCurrentTheme(ThemeSecond)
                parentActivity.recreate() // применяем для всей активити и для всех дочерних фрагментов
            }
        }

    }

    companion object {
        fun newInstance() = FirstFragment()
    }


}