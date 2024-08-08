package com.boas.rian.olympicagenda.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boas.rian.olympicagenda.databinding.ActivitySelectFavouriteCountryBinding
import com.boas.rian.olympicagenda.extensions.navigate
import com.boas.rian.olympicagenda.model.Country
import com.boas.rian.olympicagenda.preferences.dataStore
import com.boas.rian.olympicagenda.preferences.selectedCountryPreferences
import com.boas.rian.olympicagenda.repository.CountryRepository
import com.boas.rian.olympicagenda.ui.adapter.CountriesAdapter
import com.boas.rian.olympicagenda.webclient.RetrofitInit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SelectFavouriteCountryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySelectFavouriteCountryBinding.inflate(layoutInflater)
    }
    private val repository by lazy {
        CountryRepository(RetrofitInit().countryService)
    }

    private val adapter by lazy {
        CountriesAdapter(this)
    }

    private val _selectedCountryId = MutableStateFlow<String?>(null)

    // The UI collects from this StateFlow to get its state updates
    val selectedCountryId: StateFlow<String?> = _selectedCountryId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        configButton()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataStore.data.collect { preferences ->
                    preferences[selectedCountryPreferences]?.let {
                        navigateToMainActivity()
                    }
                }
            }
        }
    }


    private fun initRecyclerView() {
        binding.activitySelectFavouriteCountryRecyclerView.adapter = adapter
        adapter.onClick = { country: Country ->
            _selectedCountryId.value = country.id
        }
        lifecycleScope.launch {
            val countries = repository.getAll()
            countries?.let {
                adapter.update(it.map { country -> country.country })
            }
        }
    }

    private fun configButton() {
        val button = binding.activitySelectFavouriteCountryButton
        button.isEnabled = false
        lifecycleScope.launch {
            selectedCountryId.collect { countryId ->
                countryId?.let {
                    button.isEnabled = true
                }
            }
        }

        button.setOnClickListener {
            lifecycleScope.launch {
                selectedCountryId.value?.let { selectCountry(it) }
            }

        }
    }

    private suspend fun selectCountry(countryId: String) {
        dataStore.edit { settings ->
            settings[selectedCountryPreferences] = countryId
        }
    }

    private fun navigateToMainActivity() {
        navigate(ListEventsActivity::class.java) {
            this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            finish()
        }
    }
}