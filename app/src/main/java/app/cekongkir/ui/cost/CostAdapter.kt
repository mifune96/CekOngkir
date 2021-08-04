package app.cekongkir.ui.cost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterCostBinding
import app.cekongkir.network.responses.CostResponse

class CostAdapter (
        var results: ArrayList<CostResponse.Rajaongkir.Results>
): RecyclerView.Adapter<CostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterCostBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.binding.textCode.text = result.code
        holder.binding.textName.text = result.name
        holder.binding.listService.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = ServiceAdapter( result.costs )
        }
    }

    class ViewHolder(val binding: AdapterCostBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<CostResponse.Rajaongkir.Results>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }
}