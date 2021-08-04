package app.cekongkir.ui.tracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.network.RajaOngkirRepository

class WaybillViewModelFactory(
    val repository: RajaOngkirRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WaybillViewModel(repository) as T
    }
}