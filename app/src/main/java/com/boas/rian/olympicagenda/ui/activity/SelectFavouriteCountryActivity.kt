package com.boas.rian.olympicagenda.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.boas.rian.olympicagenda.databinding.ActivitySelectFavouriteCountryBinding
import com.boas.rian.olympicagenda.ui.adapter.CountriesAdapter
import com.boas.rian.olympicagenda.model.Country
import com.boas.rian.olympicagenda.repository.CountryRepository
import com.boas.rian.olympicagenda.webclient.RetrofitInit
import kotlinx.coroutines.launch

class SelectFavouriteCountryActivity: AppCompatActivity(){
    private val binding by lazy {
        ActivitySelectFavouriteCountryBinding.inflate(layoutInflater)
    }
    private val repository by lazy {
        CountryRepository(RetrofitInit().countryService)
    }

    private val adapter by lazy {
        CountriesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.activitySelectFavouriteCountryRecyclerView.adapter = adapter

        lifecycleScope.launch {
            val countries = repository.getAll()
            countries?.let {
                adapter.update(it.map { country -> country.country })
            }
        }
    }
}