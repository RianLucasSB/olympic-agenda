package com.boas.rian.olympicagenda.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boas.rian.olympicagenda.databinding.ActivityListEventsBinding
import com.boas.rian.olympicagenda.extensions.toFormattedString
import com.boas.rian.olympicagenda.repository.EventRepository
import com.boas.rian.olympicagenda.ui.adapter.EventsAdapter
import com.boas.rian.olympicagenda.ui.dialog.DatePickerDialog
import com.boas.rian.olympicagenda.webclient.EventWebClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ListEventsActivity : AppCompatActivity() {
    private val eventRepository by lazy {
        EventRepository(EventWebClient())
    }

    private val binding by lazy {
        ActivityListEventsBinding.inflate(layoutInflater)
    }

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())

    private val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val adapter = EventsAdapter(this)
    private var page = 1
    private var totalPages = 0
    private var loading = false
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        configDatePicker()
        loadEvents()
    }

    private fun configDatePicker() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectedDate.collect {
                    binding.data.setText(it.toFormattedString())
                    page = 1
                }
            }
        }

        binding.data.setOnClickListener {
            DatePickerDialog().show(supportFragmentManager) {
                _selectedDate.value = it
                loadEvents()
            }
        }
    }

    private fun initRecyclerView() {
        val listEventsRecyclerView = binding.listEventsRecyclerView

        listEventsRecyclerView.adapter = adapter
        layoutManager = listEventsRecyclerView.layoutManager as LinearLayoutManager
        configOnScrollEnd(listEventsRecyclerView)
    }

    private fun loadEvents() {
        lifecycleScope.launch {
            val events = eventRepository.getAll(page, selectedDate.value)
            events?.data?.let {
                adapter.update(it)
                page = events.meta.currentPage!!
                totalPages = events.meta.lastPage!!
            }
        }
    }

    private fun loadMoreEvents() {
        val progressIndicator = binding.listEventsProgressIndicator
        progressIndicator.visibility = View.VISIBLE
        loading = true
        lifecycleScope.launch {
            val events = eventRepository.getAll(page + 1, selectedDate.value)
            events?.data?.let {
                adapter.add(it)
                page = events.meta.currentPage!!
            }
            loading = false
            progressIndicator.visibility = View.GONE
        }
    }

    private fun configOnScrollEnd(listEventsRecyclerView: RecyclerView) {
        listEventsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (page < totalPages) {
                        val visibleCount = layoutManager.childCount
                        val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        val total = adapter.itemCount
                        if (!loading && (visibleCount + pastVisibleItems) >= total) {
                            loadMoreEvents()
                        }
                    }
                }
            }
        })
    }
}