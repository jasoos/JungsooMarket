package com.android.jungsoomarket

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.android.jungsoomarket.mlkit.CameraXScanningActivity
import com.android.jungsoomarket.room.AppDatabase
import com.android.jungsoomarket.room.Product
import com.android.jungsoomarket.zxing.QRCaptureActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.zxing.integration.android.IntentIntegrator
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var scanViewModel: ScanViewModel
    private var lastClickActionTime: Long = 0
    private var db: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        scanViewModel = ScanViewModel.getInstance(application)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Products"
        ).build()
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            scanner()
        }

        findViewById<FloatingActionButton>(R.id.ml_api).setOnClickListener {
            startActivity(Intent(this, CameraXScanningActivity::class.java))
        }
        Thread {
            if (db?.productDao()?.getAll()?.isEmpty() == true)
                updateDatabase()
        }.start()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun scanner() {
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false)
        integrator.captureActivity = QRCaptureActivity::class.java
        integrator.initiateScan()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val thisClickActionTime: Long = System.currentTimeMillis()
                if (thisClickActionTime - lastClickActionTime < 1000) {
                    return
                }
                lastClickActionTime = thisClickActionTime
                scanViewModel.productScanned(result.contents)
            }
        }
    }

    private fun updateDatabase() {
        var jsonString = ""
        try {
            jsonString = assets.open("products.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        val collectionType = object : TypeToken<List<Product>>() {
        }.type
        val products = Gson()
            .fromJson(jsonString, collectionType) as List<Product>

        for (product in products) {
            db?.productDao()?.insert(product)
        }
    }
}