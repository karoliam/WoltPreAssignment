package com.karoliinamultas.woltpreassignment.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    @SerialName("id")
    val id: String,
    @SerialName("location")
    val location: List<Double>,
    @SerialName("name")
    val name: String,
    @SerialName("short_description")
    val shortDescription: String,
)