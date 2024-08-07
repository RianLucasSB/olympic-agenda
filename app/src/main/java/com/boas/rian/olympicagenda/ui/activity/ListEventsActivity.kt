package com.boas.rian.olympicagenda.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boas.rian.olympicagenda.R
import com.boas.rian.olympicagenda.preferences.dataStore
import com.boas.rian.olympicagenda.preferences.selectedCountryPreferences
import com.boas.rian.olympicagenda.repository.EventRepository
import com.boas.rian.olympicagenda.webclient.EventWebClient
import kotlinx.coroutines.launch

class ListEventsActivity : AppCompatActivity() {
    private val eventRepository by lazy {
        EventRepository(EventWebClient())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_events)

        lifecycleScope.launch {
            launch {
                val events = eventRepository.getAll()
                Log.i("ListEventsActivity", "onCreate: $events")
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataStore.data.collect { preferences ->
                    val s = preferences[selectedCountryPreferences]

                    Log.i("ListEventsActivity", "onCreate: $s")
                }
            }

        }
    }
}