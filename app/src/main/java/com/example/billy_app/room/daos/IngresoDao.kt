package com.example.billy_app.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.billy_app.room.entities.Ingreso

@Dao
interface IngresoDao {

    @Insert
    suspend fun insertIngreso(ingreso: Ingreso)

    @Update
    suspend fun updateIngreso(ingreso: Ingreso)

    @Delete
    suspend fun deleteIngreso(ingreso: Ingreso)

    @Query("SELECT * FROM ingreso")
    fun getAllIngresos(): LiveData<Array<Ingreso>>

    @Query("SELECT * FROM ingreso WHERE id = :id")
    fun getByIdIngresos(id: Int): LiveData<Ingreso>

}