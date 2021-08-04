package app.cekongkir.ui.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.database.preferences.*
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.CostResponse
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.responses.CityResponse
import app.cekongkir.network.responses.SubdistrictResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class CostViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val preferences: MutableLiveData<List<PreferencesModel>> = MutableLiveData()
    val costResponse: MutableLiveData<Resource<CostResponse>> = MutableLiveData()

    fun getPreferences () {
        preferences.value = repository.getPreferences()
    }

    fun fetchCost(
            origin: String, originType: String, destination: String,
            destinationType: String, weight: String, courier: String
    ) = viewModelScope.launch {
        costResponse.postValue(Resource.Loading())
        try {
            val response = repository.fetchCost( origin, originType, destination, destinationType, weight, courier)
            costResponse.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            costResponse.postValue(Resource.Error(e.message.toString()))
            Timber.e(e)
        }
    }

}