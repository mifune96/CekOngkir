package app.cekongkir.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterCityBinding
import app.cekongkir.network.responses.CityResponse
import timber.log.Timber
import kotlin.collections.ArrayList

class CityAdapter (
        var cities: ArrayList<CityResponse.Rajaongkir.Results>,
        var listener: OnAdapterListener
): RecyclerView.Adapter<CityAdapter.ViewHolder>(), Filterable {

    private var citiesFilter = ArrayList<CityResponse.Rajaongkir.Results>()

    init {
        citiesFilter = cities
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            AdapterCityBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun getItemCount() = citiesFilter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = citiesFilter[position]
        holder.binding.textName.text = city.city_name
        holder.binding.container.setOnClickListener {
            listener.onClick(city)
        }
    }

    class ViewHolder(val binding: AdapterCityBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<CityResponse.Rajaongkir.Results>) {
        cities.clear()
        cities.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: CityResponse.Rajaongkir.Results)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Timber.d("charSearch: $charSearch")
                if (charSearch.isEmpty()) {
                    citiesFilter = cities
                } else {
                    val citiesFiltered = ArrayList<CityResponse.Rajaongkir.Results>()
                    for (city in cities) {
                        if (city.city_name.toLowerCase().contains(charSearch.toLowerCase())) {
                            citiesFiltered.add(city)
                        }
                    }
                    citiesFilter = citiesFiltered
                }
                val citiesFilteredResult = FilterResults()
                citiesFilteredResult.values = citiesFilter
                return citiesFilteredResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                citiesFilter = results?.values as ArrayList<CityResponse.Rajaongkir.Results>
                notifyDataSetChanged()
            }

        }
    }
}