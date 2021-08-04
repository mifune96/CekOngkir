package app.cekongkir.ui.waybill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cekongkir.database.persistence.WaybillEntity
import app.cekongkir.databinding.AdapterWaybillBinding

class WaybillAdapter (var waybills: ArrayList<WaybillEntity>, val listener: OnAdapterListener):
        RecyclerView.Adapter<WaybillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            AdapterWaybillBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            )
    )

    override fun getItemCount() = waybills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val waybill = waybills[position]
        holder.binding.textWaybill.text = waybill.waybill
        holder.binding.textStatus.text = waybill.status
        holder.binding.textCourier.text = waybill.courier
        holder.binding.container.setOnClickListener {  listener.onDetail(waybill) }
        holder.binding.container.setOnLongClickListener {
            listener.onDelete(waybill)
            true
        }
    }

    class ViewHolder(val binding: AdapterWaybillBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<WaybillEntity>) {
        waybills.clear()
        waybills.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onDetail(waybill: WaybillEntity)
        fun onDelete(waybill: WaybillEntity)
    }
}