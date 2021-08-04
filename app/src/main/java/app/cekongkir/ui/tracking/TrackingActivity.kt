package app.cekongkir.ui.tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TrackingActivity : AppCompatActivity() , KodeinAware {

    override val kodein by kodein()
    private val waybillViewModelFactory: WaybillViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        setupView()
        setupViewModel()
    }

    private fun setupView(){
        supportActionBar!!.title = "Lacak No. Resi"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel(){
        ViewModelProvider(this, waybillViewModelFactory).get(WaybillViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (intent.getBooleanExtra("is_tracking", false)) finish()
        else onBackPressed()
        return super.onSupportNavigateUp()
    }
}