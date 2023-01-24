package com.karoliinamultas.woltpreassignment.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VenueData(

    @SerialName("sections")
    val sections: List<Section>,

)