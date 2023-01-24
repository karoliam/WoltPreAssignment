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
import com.karoliinamultas.woltpreassignment.ui.VenuesNearApp
import com.karoliinamultas.woltpreassignment.ui.screens.LocationViewModel
import com.karoliinamultas.woltpreassignment.ui.screens.NoPermission
import com.karoliinamultas.woltpreassignment.ui.screens.VenueViewModel
import com.karoliinamultas.woltpreassignment.ui.theme.WoltPreAssignmentTheme
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var venueViewModel: VenueViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // ViewModels
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        venueViewModel = ViewModelProvider(this).get(VenueViewModel::class.java)

        sharedPreferences = getSharedPreferences("favourites", Context.MODE_PRIVATE)
        // showing HomeScreen if location permission is granted
            setView()
            requestLocationPermission()
        }

    // Location permission result
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
            getString(R.string.need_location),
            REQUEST_CODE, ACCESS_FINE_LOCATION)
    }
    // Show NoPermission screen if location permission is denied
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this)
            setContent{
                WoltPreAssignmentTheme() {
                    NoPermission()
                }
            }
        } else {
            requestLocationPermission()
        }
    }
    // Show home page if location permission is granted
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
                    venueViewModel.getVenues(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude
                    )
                }
                WoltPreAssignmentTheme {
                    VenuesNearApp(sharedPreferences)
                }
            }
        }
    }
}


