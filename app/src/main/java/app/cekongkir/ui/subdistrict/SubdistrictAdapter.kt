package app.cekongkir.ui.subdistrict

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterSubdistrictBinding
import app.cekongkir.network.responses.SubdistrictResponse

class SubdistrictAdapter (
        var results: ArrayList<SubdistrictResponse.Rajaongkir.Results>,
        var listener: OnAdapterListener
): RecyclerView.Adapter<SubdistrictAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
            AdapterSubdistrictBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.binding.textName.text = result.subdistrict_name
        holder.binding.container.setOnClickListener {
            listener.onClick(result)
        }
    }

    class ViewHolder(val binding: AdapterSubdistrictBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<SubdistrictResponse.Rajaongkir.Results>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: SubdistrictResponse.Rajaongkir.Results)
    }
}