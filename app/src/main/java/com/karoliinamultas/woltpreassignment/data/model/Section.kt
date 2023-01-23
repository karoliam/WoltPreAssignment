package com.karoliinamultas.woltpreassignment.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Section(
    @SerialName("items")
    val items: List<Item>? = null,
)