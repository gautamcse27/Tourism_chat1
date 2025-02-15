package com.example.tourismchat1.data.models
import com.google.gson.annotations.SerializedName

data class TouristPlace(
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("description") val description: String
)
