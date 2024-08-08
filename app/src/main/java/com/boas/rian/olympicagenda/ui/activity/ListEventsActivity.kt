package com.boas.rian.olympicagenda.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boas.rian.olympicagenda.databinding.ActivityListEventsBinding
import com.boas.rian.olympicagenda.preferences.dataStore
import com.boas.rian.olympicagenda.preferences.selectedCountryPreferences
import com.boas.rian.olympicagenda.repository.EventRepository
import com.boas.rian.olympicagenda.ui.adapter.EventsAdapter
import com.boas.rian.olympicagenda.webclient.EventWebClient
import kotlinx.coroutines.launch

class ListEventsActivity : AppCompatActivity() {
    private val eventRepository by lazy {
        EventRepository(EventWebClient())
    }

    private val binding by lazy {
        ActivityListEventsBinding.inflate(layoutInflater)
    }

    private val adapter = EventsAdapter(this)
    private var page = 1
    private var totalPages = 0
    private var loading = false
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val listEventsRecyclerView = binding.listEventsRecyclerView
        listEventsRecyclerView.adapter = adapter

        layoutManager = listEventsRecyclerView.layoutManager as LinearLayoutManager

        lifecycleScope.launch {
            launch {
                val events = eventRepository.getAll()
                events?.data?.let {
                    adapter.update(it)
                    page = events.meta.currentPage!!
                    totalPages = events.meta.lastPage!!
                }
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataStore.data.collect { preferences ->
                    val s = preferences[selectedCountryPreferences]

                    Log.i("ListEventsActivity", "onCreate: $s")
                }
            }

        }

        listEventsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (page < totalPages) {
                        Log.i(
                            "EventsList",
                            "initRecyclerView: page=$page totalPages=$totalPages"
                        )
                        val visibleCount = layoutManager.childCount
                        val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        val total = adapter.itemCount
                        if (!loading && (visibleCount + pastVisibleItems) >= total) {

                            loading = true
                            lifecycleScope.launch {
                                val events = eventRepository.getAll(page + 1)
                                events?.data?.let {
                                    adapter.add(it)
                                    page = events.meta.currentPage!!
                                }
                                loading = false
                            }
                        }
                    }
                }
            }
        })

    }
}