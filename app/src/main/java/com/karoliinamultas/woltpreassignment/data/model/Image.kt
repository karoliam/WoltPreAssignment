package com.karoliinamultas.woltpreassignment.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("url")
    val url: String
)