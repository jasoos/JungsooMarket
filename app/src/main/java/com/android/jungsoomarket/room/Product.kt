package com.android.jungsoomarket.room


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_tbl")
data class Product(
    @PrimaryKey @NonNull var id: String = "001",
    @ColumnInfo(name = "qrUrl")var qrUrl: String? = null,
    @ColumnInfo(name = "thumbnail") var thumbnail: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "price")var price: String? = null
)
