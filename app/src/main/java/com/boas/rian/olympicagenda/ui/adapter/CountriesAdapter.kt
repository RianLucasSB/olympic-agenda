package com.boas.rian.olympicagenda.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.boas.rian.olympicagenda.R
import com.boas.rian.olympicagenda.databinding.SelectCountryItemBinding
import com.boas.rian.olympicagenda.model.Country
import com.boas.rian.olympicagenda.utils.Animate
import com.boas.rian.olympicagenda.webclient.model.country.CountryResponse

class CountriesAdapter(
    private val context: Context,
    countries: List<Country> = emptyList(),
    var onClick: (country: Country) -> Unit = {}
): RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    private val countries = countries.toMutableList()
    private var selectedCountryId: String? = null

    inner class ViewHolder(
        private val binding: SelectCountryItemBinding,
        private val onClick: (country: Country) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        private lateinit var country: Country

        init {
            itemView.setOnClickListener {
                if (::country.isInitialized) {
                    if(selectedCountryId == country.id){
                        return@setOnClickListener
                    }
                    selectedCountryId = country.id
                    onClick(country)
                    notifyDataSetChanged()
                }
            }
        }

        fun bind(country: Country, position: Int){
            this.country = country
            Log.i("CountriesAdapter", "bind: ${country.name}")
            binding.selectCountryItemNome.text = country.name
            binding.selectCountryItemImageView.load(country.flag)
            if(selectedCountryId == country.id){
                Animate.animateColorChange(binding.selectCountryItemContainer, 0, R.color.purple_700)
            } else {
                binding.selectCountryItemContainer.setBackgroundColor(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.ViewHolder {
        return ViewHolder(SelectCountryItemBinding.inflate(LayoutInflater.from(context)), onClick)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountriesAdapter.ViewHolder, position: Int) {
        holder.bind(countries[position], position)
    }

    fun update(countriesList: List<Country>) {
        notifyItemRangeRemoved(0, this.countries.size)
        this.countries.clear()
        this.countries.addAll(countriesList)
        notifyItemInserted(this.countries.size)
    }
}