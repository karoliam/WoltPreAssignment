package com.karoliinamultas.woltpreassignment


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.karoliinamultas.woltpreassignment.ui.RestaurantsNearApp
import com.karoliinamultas.woltpreassignment.ui.screens.LocationViewModel
import com.karoliinamultas.woltpreassignment.ui.screens.RestaurantViewModel

import com.karoliinamultas.woltpreassignment.ui.theme.WoltPreAssignmentTheme

class MainActivity : ComponentActivity() {
    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        restaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("favourites", Context.MODE_PRIVATE)
        setContent {
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
            }
        }
    }


