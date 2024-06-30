package com.kenant42.kotlinlocationsstudy

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.kenant42.kotlinlocationsstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isGranted = 0
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationTask: Task<Location>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.buttonGetLocation.setOnClickListener {
            isGranted = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (isGranted != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
            } else {
                locationTask = fusedLocationProviderClient.lastLocation
                getLocation()
            }
        }
    }

    fun getLocation() {
        locationTask.addOnSuccessListener {
            if (it != null) {
                binding.latitude.text = "Latitude ${it.latitude}"
                binding.longitude.text = "Longitude ${it.longitude}"
            } else {

            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            isGranted = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show()
                locationTask = fusedLocationProviderClient.lastLocation
                getLocation()
            } else {
                Toast.makeText(applicationContext, "PERMISSION DENIED", Toast.LENGTH_SHORT).show()
            }
        } else {

        }
    }
}