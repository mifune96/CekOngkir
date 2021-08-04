package app.cekongkir.ui.cost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.network.RajaOngkirRepository

class CostViewModelFactory(
    val repository: RajaOngkirRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CostViewModel(repository) as T
    }
}