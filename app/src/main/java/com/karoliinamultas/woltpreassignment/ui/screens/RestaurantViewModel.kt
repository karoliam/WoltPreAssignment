package com.karoliinamultas.woltpreassignment.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.karoliinamultas.woltpreassignment.data.model.Item
import com.karoliinamultas.woltpreassignment.data.model.Venue
import com.karoliinamultas.woltpreassignment.data.network.RestaurantApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface RestaurantUiState {
    data class Success(val restaurants: MutableList<List<Item>?>) : RestaurantUiState
    object Error : RestaurantUiState
    object Loading : RestaurantUiState
}

class RestaurantViewModel() : ViewModel() {
    var restaurantUiState: RestaurantUiState by mutableStateOf(RestaurantUiState.Loading)
        private set



    fun getRestaurants(cLatitude: String, cLongitude: String) {
        viewModelScope.launch {
            restaurantUiState = try {
                println("tossa lokatio $cLatitude ja $cLongitude")
                val listResult = RestaurantApi.retrofitService.getRestaurants(cLatitude, cLongitude)
                val restaurantNameList = mutableListOf<List<Item>?>()
                for(item in listResult.sections) {
                    restaurantNameList.add(item.items)
                }
                println("restaurantList $restaurantNameList")
                RestaurantUiState.Success(restaurantNameList)
            } catch (e: IOException) {
                RestaurantUiState.Error
            } catch (e: HttpException) {
                RestaurantUiState.Error
            }

        }
    }

}
