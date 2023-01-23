package com.karoliinamultas.woltpreassignment.ui.screens

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.karoliinamultas.woltpreassignment.R
import com.karoliinamultas.woltpreassignment.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL


@Composable
fun HomeScreen(
    restaurantUiState: RestaurantUiState,
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferences
) {
    when (restaurantUiState) {
        is RestaurantUiState.Loading -> LoadingScreen(modifier)
        is RestaurantUiState.Success -> ResultScreen(restaurantUiState.restaurants, sharedPreferences)
        is RestaurantUiState.Error -> ErrorScreen(modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(stringResource(R.string.loading))
        }
    }

}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column{
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(stringResource(R.string.failed_to_load))
        }
    }

}


@Composable
fun ShowImage(urlText: URL) {
    var savedBitmap by remember { mutableStateOf(Bitmap.createBitmap(1,1,Bitmap.Config.ALPHA_8)) }
    LaunchedEffect(urlText) {
        savedBitmap = getImage(urlText)
    }
    Image(
        bitmap = savedBitmap.asImageBitmap(),
        contentDescription = stringResource(R.string.restaurant_image),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(56.dp)
    )
}


// reads a file
private suspend fun getImage(url: URL): Bitmap =
    withContext(Dispatchers.IO) {
        val myConn = url.openStream()
        val bit = BitmapFactory.decodeStream(myConn);
        return@withContext bit
    }


@Composable
fun FavoriteButton(modifier: Modifier, sharedPreferences: SharedPreferences, buttonId: String) {

    fun isIconClicked(id: String) : Boolean{
        return sharedPreferences.getBoolean(id, false)
    }

    var isFavorite by remember { mutableStateOf(isIconClicked(buttonId)) }

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
            sharedPreferences.edit().putBoolean(buttonId, isFavorite).apply()
        }
    ) {



        Icon(
            contentDescription = stringResource(R.string.favourite_button),
            tint = MaterialTheme.colorScheme.secondary,

            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
        )
    }

}

@Composable
fun ResultScreen(restaurantUiState: List<List<Item>?>, sharedPreferences: SharedPreferences) {
    val filteredItems = restaurantUiState.filterNotNull().flatten()
    val notNullRestaurants = filteredItems.filter { item -> item.venue?.name !== null }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(color = colorResource(R.color.white)) {
            Column() {
                Text(
                    text = stringResource(R.string.new_venues),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(R.color.text_black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
                )

                LazyColumn() {
                    items(notNullRestaurants.take(15)) { section ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(R.color.white)
                            ),
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Row() {
                                val stringUrl = URL(section.image.url)
                                ShowImage(urlText = stringUrl)
                                Column {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {

                                        Column(modifier = Modifier.weight(1f)) {
                                            section.venue?.let {
                                                Text(
                                                    text = it.name,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    color = colorResource(R.color.text_black),
                                                    modifier = Modifier
                                                        .padding(top = 25.dp, start = 16.dp)
                                                )
                                            }

                                            section.venue?.let {
                                                Text(
                                                    text = it.shortDescription,
                                                    color = colorResource(R.color.text_black),
                                                    style = MaterialTheme.typography.bodySmall,
                                                    modifier = Modifier
                                                        .padding(bottom = 25.dp, start = 16.dp)
                                                )
                                            }
                                        }
                                        section.venue?.let {
                                            FavoriteButton(
                                                modifier = Modifier
                                                    .size(24.dp, 21.01.dp)
                                                    .weight(1f)
                                                    .fillMaxSize()
                                                    .padding(end = 18.dp), sharedPreferences,
                                                buttonId = it.id
                                            )
                                        }

                                    }
                                    Divider(
                                        color = colorResource(R.color.divider),
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .fillMaxWidth()
                                    )
                                }

                            }

                        }


                    }


                }
            }


        }
    }

}




