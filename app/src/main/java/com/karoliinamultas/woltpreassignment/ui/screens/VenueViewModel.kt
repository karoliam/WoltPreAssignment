package com.karoliinamultas.woltpreassignment.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karoliinamultas.woltpreassignment.data.model.Item
import com.karoliinamultas.woltpreassignment.data.network.VenueApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface VenuesUiState {
    data class Success(val venues: MutableList<List<Item>?>) : VenuesUiState
    object Error : VenuesUiState
    object Loading : VenuesUiState
}

class VenueViewModel() : ViewModel() {
    var venuesUiState: VenuesUiState by mutableStateOf(VenuesUiState.Loading)
        private set


    fun getVenues(cLatitude: String, cLongitude: String) {
        viewModelScope.launch {
            venuesUiState = try {
                val listResult = VenueApi.retrofitService.getVenues(cLatitude, cLongitude)
                val venueNameList = mutableListOf<List<Item>?>()

                // iterating the listResult and adding only sections.items to a new list
                for(item in listResult.sections) {
                    venueNameList.add(item.items)
                }
                // passing the new list to Success
                VenuesUiState.Success(venueNameList)
            } catch (e: IOException) {
                VenuesUiState.Error
            } catch (e: HttpException) {
                VenuesUiState.Error
            }

        }
    }

}
