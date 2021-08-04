package app.cekongkir.ui.subdistrict

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.databinding.FragmentSubdistrictBinding
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.SubdistrictResponse
import app.cekongkir.ui.city.CityViewModel
import app.cekongkir.utils.*

class SubdistrictFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CityViewModel::class.java)
    }
    private lateinit var binding: FragmentSubdistrictBinding
    private lateinit var subdistrictAdapter: SubdistrictAdapter
    private val type by lazy { requireActivity().intent.getStringExtra( "intent_type" )!! }
    private val cityId by lazy { requireArguments().getString("city_id")!! }
    private val cityName by lazy { requireArguments().getString("city_name")!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSubdistrictBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
//        viewModel.fetchSubdistrict( cityId )
    }

    private fun setupView(){
        viewModel.titleBar.postValue("Pilih Kecamatan")
    }

    private fun setupRecyclerView(){
        subdistrictAdapter = SubdistrictAdapter(arrayListOf(), object : SubdistrictAdapter.OnAdapterListener {
            override fun onClick(result: SubdistrictResponse.Rajaongkir.Results) {
                viewModel.savePreferences(
                        type = type,
                        id = result.subdistrict_id,
                        name = "$cityName, ${result.subdistrict_name}"
                )
                requireActivity().finish()
            }
        })
        binding.listSubdistrict.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = subdistrictAdapter
        }
    }

    private fun setupListener(){
        binding.refreshSubdistrict.setOnRefreshListener {
            viewModel.fetchSubdistrict( cityId )
        }
    }

    private fun setupObserver(){
        viewModel.subdistrictResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Loading -> binding.refreshSubdistrict.swipeShow()
                is Resource.Success -> {
                    binding.refreshSubdistrict.swipeHide()
                    subdistrictAdapter.setData( it.data!!.rajaongkir.results )
                }
                is Resource.Error -> {
                    binding.refreshSubdistrict.swipeHide()
                    requireActivity().showToast(resources.getString(R.string.message_error))
                }
            }
        })
    }
}