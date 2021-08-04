package app.cekongkir.ui.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import app.cekongkir.R
import app.cekongkir.databinding.FragmentTrackingBinding
import app.cekongkir.utils.showToast

private const val SICEPAT = "000421030654"

class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding
    private var courier: String = ""
    private var waybill: String = ""
    private val isTracking by lazy { requireActivity().intent.getBooleanExtra("is_tracking", false) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTest()
        setupSpinner()
        setupListener()
    }

    private fun setupTest(){
        binding.editWaybill.setText( SICEPAT )
    }

    private fun setupSpinner(){
        val courierAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.courier, android.R.layout.simple_spinner_item
        )
        courierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.listCourier.adapter = courierAdapter
    }

    private fun setupListener(){
        if (isTracking) {

            waybill = requireActivity().intent.getStringExtra("is_waybill")!!
            courier = requireActivity().intent.getStringExtra("is_courier")!!

            findNavController().navigate(
                    R.id.action_trackingFragment_to_trackingResultFragment,
                    bundleOf( "waybill" to waybill, "courier" to courier )
            )
        }
        binding.buttonTrack.setOnClickListener {

            courier = binding.listCourier.selectedItem.toString()
            waybill = binding.editWaybill.text.toString()

            if (waybill.isNotEmpty() && courier.isNotEmpty() ) {
                findNavController().navigate(
                        R.id.action_trackingFragment_to_trackingResultFragment,
                        bundleOf( "waybill" to waybill, "courier" to courier )
                )
            } else showToast("Lengkapi No. Resi dan Kurir")
        }
    }
}