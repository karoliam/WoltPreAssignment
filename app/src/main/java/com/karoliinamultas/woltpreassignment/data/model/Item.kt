package com.karoliinamultas.woltpreassignment.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("image")
    val image: Image,
    @SerialName("venue")
    val venue: Venue? = null
)