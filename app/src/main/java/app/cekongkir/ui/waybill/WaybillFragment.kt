package app.cekongkir.ui.waybill

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.database.persistence.WaybillEntity
import app.cekongkir.databinding.FragmentWaybillBinding
import app.cekongkir.ui.tracking.TrackingActivity
import app.cekongkir.ui.tracking.WaybillViewModel
import timber.log.Timber

class WaybillFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(WaybillViewModel::class.java)
    }
    private lateinit var binding: FragmentWaybillBinding
    private lateinit var waybillAdapter: WaybillAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWaybillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupRecyclerView(){
        waybillAdapter = WaybillAdapter(arrayListOf(), object : WaybillAdapter.OnAdapterListener {
            override fun onDetail(waybillEntity: WaybillEntity) {
                startActivity(
                        Intent(requireActivity(), TrackingActivity::class.java)
                                .putExtra("is_tracking", true)
                                .putExtra("is_waybill", waybillEntity.waybill)
                                .putExtra("is_courier", waybillEntity.courier)
                )
            }
            override fun onDelete(waybillEntity: WaybillEntity) {
                val builder = AlertDialog.Builder(requireActivity())
                builder.apply {
                    setTitle("HAPUS NO. RESI")
                    setMessage("Hapus No. Resi ${waybillEntity.waybill}?")
                    setPositiveButton("Hapus") { _, _ ->
                        viewModel.deleteWaybill( waybillEntity )
                        Toast.makeText(requireActivity(), "Resi dihapus", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("batal") { _, _ ->

                    }
                    show()
                }
            }
        })
        binding.listWaybill.apply {
            adapter = waybillAdapter
        }
    }

    private fun setupListener(){
        binding.editWaybill.setOnClickListener {
            startActivity( Intent(requireContext(), TrackingActivity::class.java))
        }
    }

    private fun setupObserver(){
        viewModel.waybill.observe(viewLifecycleOwner, {
            waybillAdapter.setData( it )
        })
    }
}