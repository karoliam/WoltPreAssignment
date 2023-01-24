package com.karoliinamultas.woltpreassignment


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.karoliinamultas.woltpreassignment.ui.RestaurantsNearApp
import com.karoliinamultas.woltpreassignment.ui.screens.LocationViewModel
import com.karoliinamultas.woltpreassignment.ui.screens.NoPermission
import com.karoliinamultas.woltpreassignment.ui.screens.RestaurantViewModel
import com.karoliinamultas.woltpreassignment.ui.theme.WoltPreAssignmentTheme
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        restaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        sharedPreferences = getSharedPreferences("favourites", Context.MODE_PRIVATE)

            setView()
            requestLocationPermission()

        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(this, ACCESS_FINE_LOCATION)

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(this,
            "This application needs to access your location to function correctly.",
            REQUEST_CODE, ACCESS_FINE_LOCATION)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this)
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        setView()
    }
    private fun setView() {
        setContent {
            if (hasLocationPermission()) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.White,
                        darkIcons = true
                    )
                }
                val currentLocation by locationViewModel.getLocationLiveData().observeAsState()

                if (currentLocation != null) {
                    restaurantViewModel.getRestaurants(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude
                    )
                }
                WoltPreAssignmentTheme {
                    RestaurantsNearApp(sharedPreferences)
                }
            } else {
                WoltPreAssignmentTheme() {
                    NoPermission()
                }
            }
        }
    }
}


