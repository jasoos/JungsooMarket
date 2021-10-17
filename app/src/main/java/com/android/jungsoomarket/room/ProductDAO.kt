package com.android.jungsoomarket.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDAO {
    @Query("SELECT * FROM product_tbl")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product_tbl WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<Product>

    @Query("SELECT * FROM product_tbl WHERE id IN (:ids) LIMIT 1")
    fun fetchById(ids: String): Product

    @Insert
    fun insert(vararg product: Product)

    @Delete
    fun delete(user: Product)
}