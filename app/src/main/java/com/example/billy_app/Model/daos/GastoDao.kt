package com.example.billy_app.Model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.billy_app.Model.entities.Gasto

@Dao
interface GastoDao {

    @Insert
    suspend fun insertGasto(gasto: Gasto)

    @Update
    suspend fun updateGasto(gasto: Gasto)

    @Delete
    suspend fun deleteGasto(gasto: Gasto)

    @Query("SELECT * FROM gasto")
    fun getAllGastos(): LiveData<List<Gasto>>

    @Query("SELECT * FROM gasto WHERE id = :id")
    fun getByIdGasto(id: Int): LiveData<Gasto>
}