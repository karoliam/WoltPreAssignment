package com.karoliinamultas.woltpreassignment.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.karoliinamultas.woltpreassignment.ui.screens.HomeScreen
import com.karoliinamultas.woltpreassignment.ui.screens.VenueViewModel

@Composable
fun VenuesNearApp(sharedPreferences: SharedPreferences) {
    val venueViewModel: VenueViewModel = viewModel()
    HomeScreen(
        venuesUiState = venueViewModel.venuesUiState,
        modifier = Modifier,
        sharedPreferences
    )
}