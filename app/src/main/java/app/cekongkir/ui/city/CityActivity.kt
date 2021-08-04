package app.cekongkir.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CityActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val cityViewModelFactory: CityViewModelFactory by instance()
    private lateinit var cityViewModel: CityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        setupView()
        setupViewModel()
        setupObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupView () {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel(){
        cityViewModel = ViewModelProvider(this, cityViewModelFactory)
                .get(CityViewModel::class.java)
    }

    private fun setupObserver(){
        cityViewModel.titleBar.observe(this, {
            supportActionBar!!.title = it
        })
    }
}