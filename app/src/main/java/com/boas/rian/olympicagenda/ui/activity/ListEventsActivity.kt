package com.boas.rian.olympicagenda.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.boas.rian.olympicagenda.R
import com.boas.rian.olympicagenda.preferences.dataStore
import com.boas.rian.olympicagenda.preferences.selectedCountryPreferences
import kotlinx.coroutines.launch

class ListEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_events)

        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                val s = preferences[selectedCountryPreferences]

                Log.i("ListEventsActivity", "onCreate: $s")
            }
        }
    }
}