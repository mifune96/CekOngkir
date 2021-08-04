package app.cekongkir.ui.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.database.persistence.WaybillEntity
import app.cekongkir.network.Resource
import app.cekongkir.network.responses.WaybillResponse
import app.cekongkir.network.RajaOngkirRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class WaybillViewModel(
    private val repository: RajaOngkirRepository
) : ViewModel() {

    val waybillResponse: MutableLiveData<Resource<WaybillResponse>> = MutableLiveData()
    val waybill: LiveData<List<WaybillEntity>> = repository.getWaybill()

    fun fetchWaybill(waybill: String, courier: String) = viewModelScope.launch {
        waybillResponse.postValue(Resource.Loading())
        try {
            val response = repository.fetchWaybill(waybill, courier)
            waybillResponse.postValue(Resource.Success(response.body()!!))
            saveWaybill( response.body()!!.rajaongkir )
        } catch (e: Exception) {
            waybillResponse.postValue(Resource.Error(e.message.toString()))
            Timber.e(e)
        }
    }

    private fun saveWaybill(waybill: WaybillResponse.Rajaongkir) = viewModelScope.launch {
        repository.saveWaybill(
                WaybillEntity(
                        waybill = waybill.query.waybill ,
                        courier = waybill.query.courier,
                        status = waybill.result.delivery_status.status,
                )
        )
    }

    fun deleteWaybill (waybillEntity: WaybillEntity) = viewModelScope.launch {
        repository.deleteWaybill(waybillEntity)
    }

}