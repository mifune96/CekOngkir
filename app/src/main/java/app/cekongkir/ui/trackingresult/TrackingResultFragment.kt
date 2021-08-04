package app.cekongkir.ui.trackingresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.databinding.FragmentTrackingResultBinding
import app.cekongkir.network.Resource
import app.cekongkir.ui.tracking.WaybillViewModel
import app.cekongkir.utils.swipeHide
import app.cekongkir.utils.swipeShow
import app.cekongkir.utils.viewHide
import app.cekongkir.utils.viewShow

class TrackingResultFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(WaybillViewModel::class.java)
    }
    private lateinit var binding: FragmentTrackingResultBinding
    private val waybill by lazy { requireArguments().getString("waybill")!! }
    private val courier by lazy { requireArguments().getString("courier")!! }
    private lateinit var manifestAdapter: TrackingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackingResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchWaybill( waybill, courier )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupRecyclerView(){
        manifestAdapter = TrackingAdapter(arrayListOf())
        binding.listManifest.adapter = manifestAdapter
    }

    private fun setupListener(){
        binding.refreshWaybill.setOnRefreshListener {
            viewModel.fetchWaybill( waybill, courier )
        }
    }

    private fun setupObserver(){
        viewModel.waybillResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.refreshWaybill.swipeShow()
                    binding.container.viewHide()
                }
                is Resource.Error -> {
                    binding.refreshWaybill.swipeHide()
                    binding.container.viewShow()
                }
                is Resource.Success -> {
                    binding.refreshWaybill.swipeHide()
                    binding.container.viewShow()
                    val data = it.data!!.rajaongkir.result
                    binding.textStatus.text = data.delivery_status.status
                    binding.textReceiver.text = data.delivery_status.pod_receiver
                    binding.textDate.text = "${data.delivery_status.pod_date} ${data.delivery_status.pod_time}"
                    manifestAdapter.setData(data.manifest)
                }
            }
        })
    }
}