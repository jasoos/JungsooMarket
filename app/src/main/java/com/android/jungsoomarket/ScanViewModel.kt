package com.android.jungsoomarket

import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.android.jungsoomarket.room.AppDatabase
import com.android.jungsoomarket.room.Product

class ScanViewModel(application: Application) : AndroidViewModel(application) {
    var scanData: MutableLiveData<String> = MutableLiveData()
    private var db: AppDatabase? = null

    init {
        db = application.applicationContext?.let {
            Room.databaseBuilder(
                it,
                AppDatabase::class.java, "Products"
            ).allowMainThreadQueries().build()
        }
    }

    companion object {
        private lateinit var instance: ScanViewModel

        @MainThread
        fun getInstance(application: Application): ScanViewModel {
            instance = if (::instance.isInitialized) instance else ScanViewModel(application)
            return instance
        }
    }

    fun productScanned(value: String) {
        scanData.value = value
    }

    fun getProduct(id: String): Product? {
        return db?.productDao()?.fetchById(id)
    }

}