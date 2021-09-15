package ru.geekbrains.themechanger.kotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.geekbrains.themechanger.databinding.FragmentMainBinding
import androidx.appcompat.view.ContextThemeWrapper
import ru.geekbrains.themechanger.R


class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var parentActivity: MainActivity // 1 способ получить родительскую активити
    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = (context as MainActivity) // 1 способ получить родительскую активити

        parentActivity = activity as MainActivity // воторой способ
        parentActivity =
            requireActivity() as MainActivity // третий способ( на самом деле то же самое все, просто со встроенной проверкой актвити на null)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnThemeOne.setOnClickListener(this)
        binding.btnThemeSecond.setOnClickListener(this)
        when (parentActivity.getCurrentTheme()) {
            1 -> binding.radioGroup.check(R.id.btnThemeOne)
            2 ->  binding.radioGroup.check(R.id.btnThemeSecond)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnThemeOne -> parentActivity.setCurrentTheme(ThemeOne)
            R.id.btnThemeSecond -> parentActivity.setCurrentTheme(ThemeSecond)
        }
        parentActivity.recreate()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}