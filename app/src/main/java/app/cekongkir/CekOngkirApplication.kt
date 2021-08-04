package app.cekongkir

import android.app.Application
import app.cekongkir.database.preferences.CekOngkirPreference
import app.cekongkir.database.persistence.CekOngkirDatabase
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.ui.city.CityViewModelFactory
import app.cekongkir.ui.cost.CostViewModelFactory
import app.cekongkir.ui.tracking.WaybillViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber

class CekOngkirApplication: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@CekOngkirApplication))

        bind() from singleton { CekOngkirPreference(instance()) }
        bind() from singleton { CekOngkirDatabase(instance()) }
        bind<RajaOngkirEndpoint>() with singleton { ApiService.getClient() }

        bind() from singleton { RajaOngkirRepository( instance(), instance(), instance() ) }
        bind() from provider { CityViewModelFactory( instance()) }
        bind() from provider { CostViewModelFactory( instance()) }
        bind() from provider { WaybillViewModelFactory( instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}