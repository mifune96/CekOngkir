package app.cekongkir.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.cekongkir.R
import app.cekongkir.databinding.FragmentCityBinding
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.CityResponse
import app.cekongkir.utils.swipeHide
import app.cekongkir.utils.swipeShow

class CityFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CityViewModel::class.java)
    }
    private lateinit var binding: FragmentCityBinding
    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupView(){
        viewModel.titleBar.postValue("Pilih Kota")
        binding.editSearch.isEnabled = false
    }

    private fun setupListener(){
        binding.editSearch.doAfterTextChanged {
            cityAdapter.filter.filter( it.toString() )
        }
        binding.refreshCity.setOnRefreshListener {
            viewModel.fetchCity()
        }
    }

    private fun setupRecyclerView(){
        cityAdapter = CityAdapter(arrayListOf(), object : CityAdapter.OnAdapterListener {
            override fun onClick(result: CityResponse.Rajaongkir.Results) {
                viewModel.fetchSubdistrict( result.city_id )
                findNavController().navigate(
                        R.id.action_cityFragment_to_subdistrictFragment,
                        bundleOf(
                                "city_id" to result.city_id,
                                "city_name" to result.city_name
                        )
                )
            }
        })
        binding.listCity.apply {
            addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
            adapter = cityAdapter
        }
    }

    private fun setupObserver(){
        viewModel.cityResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> binding.refreshCity.swipeShow()
                is Resource.Success -> {
                    binding.refreshCity.swipeHide()
                    cityAdapter.setData(it.data!!.rajaongkir.results)
                    binding.editSearch.isEnabled = true
                }
                is Resource.Error -> {
                    binding.refreshCity.swipeHide()
                    Toast.makeText(context, it.message!!, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}