package app.cekongkir.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.CityResponse
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.responses.SubdistrictResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class CityViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val titleBar = MutableLiveData("")

    val cityResponse: MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val subdistrictResponse: MutableLiveData<Resource<SubdistrictResponse>> = MutableLiveData()

    init {
        fetchCity()
    }

    fun fetchCity() = viewModelScope.launch {
        cityResponse.postValue(Resource.Loading())
        try {
            val response = repository.fetchCity()
            cityResponse.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            cityResponse.postValue(Resource.Error(e.message.toString()))
            Timber.e(e)
        }
    }

    fun fetchSubdistrict(city: String) = viewModelScope.launch {
        subdistrictResponse.value = Resource.Loading()
        try {
            val response = repository.fetchSubdistrict(city)
            subdistrictResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception) {
            subdistrictResponse.value = Resource.Error(e.message.toString())
            Timber.e(e)
        }
    }

    fun savePreferences (type: String, id: String, name: String) {
        repository.savePreferences( type, id, name )
    }

}