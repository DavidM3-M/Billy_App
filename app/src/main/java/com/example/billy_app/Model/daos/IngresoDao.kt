package com.example.billy_app.Model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.billy_app.Model.entities.Ingreso
import kotlinx.coroutines.flow.Flow

@Dao
interface IngresoDao {

    @Insert
    suspend fun insertIngreso(ingreso: Ingreso)

    @Update
    suspend fun updateIngreso(ingreso: Ingreso)

    @Delete
    suspend fun deleteIngreso(ingreso: Ingreso)

    @Query("SELECT * FROM ingreso")
    fun getAllIngresos(): LiveData<List<Ingreso>>

    @Query("SELECT * FROM ingreso WHERE id = :id")
    fun getByIdIngresos(id: Int): Flow<Ingreso>

}