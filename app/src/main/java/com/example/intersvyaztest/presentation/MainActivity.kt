package com.example.intersvyaztest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.intersvyaztest.presentation.feature.notification.Notification
import com.example.itntersvyaztest.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fromSymbol = intent.getStringExtra(Notification.FROM_SYMBOL_EXTRA)

        if (fromSymbol != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CoinDetailFragment.newDetailFragment(fromSymbol))
                .addToBackStack(null)
                .commit()
        }
    }
}
