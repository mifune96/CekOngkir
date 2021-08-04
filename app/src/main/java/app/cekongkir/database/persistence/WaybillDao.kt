package app.cekongkir.database.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WaybillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(waybillEntity: WaybillEntity)

    @Update
    suspend fun update(waybillEntity: WaybillEntity)

    @Delete
    suspend fun delete(waybillEntity: WaybillEntity)

    @Query("SELECT * FROM tableWaybill")
    fun select(): LiveData<List<WaybillEntity>>
}