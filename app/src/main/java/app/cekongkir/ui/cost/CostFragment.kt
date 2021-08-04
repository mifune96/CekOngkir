package app.cekongkir.ui.cost

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.databinding.FragmentCostBinding
import app.cekongkir.network.Resource
import app.cekongkir.ui.city.CityActivity
import app.cekongkir.utils.*
import timber.log.Timber

class CostFragment : Fragment(){

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CostViewModel::class.java)
    }
    private lateinit var binding: FragmentCostBinding
    private lateinit var costAdapter: CostAdapter

    private var originSubdistricId: String? = ""
    private var destinationSubdistricId: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    override fun onStart() {
        super.onStart()
        loadingCost( false )
        viewModel.getPreferences()
    }

    private fun setupRecyclerView(){
        costAdapter = CostAdapter(arrayListOf())
        binding.listCost.adapter = costAdapter
    }

    private fun setupListener(){
        binding.editOrigin.setOnClickListener {
            startActivity(
                    Intent(requireActivity(), CityActivity::class.java)
                            .putExtra( "intent_type", "origin")
            )
        }
        binding.editDestination.setOnClickListener {
            startActivity(
                    Intent(requireActivity(), CityActivity::class.java)
                            .putExtra( "intent_type", "destination")
            )
        }
        binding.buttonCost.setOnClickListener {
            if (originSubdistricId.isNullOrEmpty() || destinationSubdistricId.isNullOrEmpty()) {
                showToast("Lengkapi data pencarian")
            } else {
                viewModel.fetchCost(
                        origin = originSubdistricId!!,
                        originType = "subdistrict",
                        destination = destinationSubdistricId!!,
                        destinationType = "subdistrict",
                        weight = "1000",
                        courier = "sicepat:jnt:pos"
                )
            }
        }
    }

    private fun loadingCost(loading: Boolean) {
        if (loading) binding.progressCost.viewShow()
        else binding.progressCost.viewHide()
    }

    private fun setupObserver(){
        viewModel.preferences.observe(viewLifecycleOwner, { preferencesList ->
            preferencesList.forEach {
                Timber.d("preferencesList: $it")
                when (it.type) {
                    "origin" -> {
                        originSubdistricId = it.id
                        binding.editOrigin.setText( it.name )
                    }
                    "destination" -> {
                        destinationSubdistricId = it.id
                        binding.editDestination.setText( it.name )
                    }
                }
            }
        })
        viewModel.costResponse.observe( viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressCost.viewShow()
                }
                is Resource.Success -> {
                    binding.progressCost.viewHide()
                    costAdapter.setData( it.data!!.rajaongkir.results )
                }
                is Resource.Error -> {
                    binding.progressCost.viewHide()
                }
            }
        })
    }
}