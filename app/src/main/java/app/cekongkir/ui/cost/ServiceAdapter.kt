package app.cekongkir.ui.cost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterServiceBinding
import app.cekongkir.network.responses.CostResponse

class ServiceAdapter (var costs: List<CostResponse.Rajaongkir.Results.Cost>):
        RecyclerView.Adapter<ServiceAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterServiceBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun getItemCount() = costs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cost = costs[position]
        holder.binding.textService.text = cost.service
        holder.binding.textDescription.text = cost.description
        holder.binding.textValue.text = cost.cost[0].value.toString()
        holder.binding.textEtd.text = cost.cost[0].etd
    }

    class ViewHolder(val binding: AdapterServiceBinding): RecyclerView.ViewHolder(binding.root)
}