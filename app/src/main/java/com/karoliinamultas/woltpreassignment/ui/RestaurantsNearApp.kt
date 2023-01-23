package com.karoliinamultas.woltpreassignment.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karoliinamultas.woltpreassignment.ui.screens.HomeScreen
import com.karoliinamultas.woltpreassignment.ui.screens.RestaurantViewModel

@Composable
fun RestaurantsNearApp(sharedPreferences: SharedPreferences) {
    val restaurantViewModel: RestaurantViewModel = viewModel()
    HomeScreen(
        restaurantUiState = restaurantViewModel.restaurantUiState,
        modifier = Modifier,
        sharedPreferences
    )
}