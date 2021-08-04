package app.cekongkir.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.databinding.ActivityHomeBinding
import app.cekongkir.ui.cost.CostViewModel
import app.cekongkir.ui.cost.CostViewModelFactory
import app.cekongkir.ui.tracking.WaybillViewModel
import app.cekongkir.ui.tracking.WaybillViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val costViewModelFactory: CostViewModelFactory by instance()
    private val waybillViewModelFactory: WaybillViewModelFactory by instance()
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupTab()
        setupViewModel()
    }

    private fun setupTab(){
        val tabTitles = arrayOf("CEK ONGKIR", "CEK RESI")
        val tabAdapter = HomeTabAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupViewModel(){
        ViewModelProvider(this, costViewModelFactory).get(CostViewModel::class.java)
        ViewModelProvider(this, waybillViewModelFactory).get(WaybillViewModel::class.java)
    }
}