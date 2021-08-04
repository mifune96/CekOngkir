package app.cekongkir.ui.trackingresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.databinding.AdapterTrackingBinding
import app.cekongkir.network.responses.WaybillResponse

class TrackingAdapter (var manifests: ArrayList<WaybillResponse.Rajaongkir.Results.Manifest>):
        RecyclerView.Adapter<TrackingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterTrackingBinding.inflate( LayoutInflater.from(parent.context), parent, false )
    )

    override fun getItemCount() = manifests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val manifest = manifests[position]
        holder.binding.textDate.text = manifest.manifest_date
        holder.binding.textDescription.text = manifest.manifest_description
    }

    class ViewHolder(val binding: AdapterTrackingBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<WaybillResponse.Rajaongkir.Results.Manifest>) {
        manifests.clear()
        manifests.addAll(data)
        notifyDataSetChanged()
    }
}