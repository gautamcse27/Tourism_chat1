package com.example.tourismchat1.data.models
import com.google.gson.annotations.SerializedName

data class TouristPlace(
    @SerializedName("id")val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_urls") val image_urls: List<String>
)
