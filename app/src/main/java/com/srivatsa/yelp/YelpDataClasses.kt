package com.srivatsa.yelp

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    //mirroring the json objects from Json file of response body
    @SerializedName("total")val total:Int,
    @SerializedName("business")val restaurants: List<YelpRestaurant>
    )

data class YelpRestaurant(
    val name:String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReviews:Int,
    @SerializedName("distamce")val distanceInMeters: Double,
    @SerializedName("ImageUrl") val imageUrl: String,
    val category: List<YelpCategory>,
    val location: YelpLocation)
{
    //convert distance in meters
    fun displayDistance():String{
        val milesPerMeter=0.000621371
        val distanceInMiles="%2f".format(distanceInMeters*milesPerMeter)
        return "$distanceInMiles mi"
    }
}

data class YelpCategory(
    val title:String
)

data class YelpLocation(
   @SerializedName("address1")val address:String
)