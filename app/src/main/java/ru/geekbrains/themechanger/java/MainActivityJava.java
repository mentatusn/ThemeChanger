package ru.geekbrains.themechanger.java;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import ru.geekbrains.themechanger.R;

public class MainActivityJava extends AppCompatActivity implements View.OnClickListener {

    static final int ThemeOne = 1;
    static final int ThemeSecond = 2;

    static final String KEY_SP = "sp";
    static final String KEY_CURRENT_THEME = "current_theme";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getRealStyle(getCurrentTheme()));
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        RadioButton radioButton1 = findViewById(R.id.btnThemeOne);
        RadioButton radioButton2 = findViewById(R.id.btnThemeSecond);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        switch (getCurrentTheme()) {
            case 1:
                radioButton1.setChecked(true);
                break;
            case 2:
                radioButton2.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnThemeOne:
                setCurrentTheme(ThemeOne);
                break;
            case R.id.btnThemeSecond:
                setCurrentTheme(ThemeSecond);
                break;
        }
        recreate();
    }

    private void setCurrentTheme(int currentTheme) {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CURRENT_THEME, currentTheme);
        editor.apply();
    }

    private int getCurrentTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE);
        return (sharedPreferences.getInt(KEY_CURRENT_THEME, -1));
    }

    private int getRealStyle(int currentTheme) {
        switch (currentTheme) {
            case ThemeOne:
                return R.style.ThemeOne;
            case ThemeSecond:
                return R.style.ThemeSecond;
            default:
                return 0;
        }
    }
}