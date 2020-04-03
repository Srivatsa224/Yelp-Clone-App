package com.srivatsa.yelp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL="https://api.yelp.com/v3/"
private const val TAG="MainActivity"
private const val API_KEY="nwbEVUvMLj_lBh0NOayoiURs61CJHT4OXywatWSqIuPxK_NOOgqznyPCAbXay6lOiT-19kfP9VDIBJHQcrKlMVz_4IiJAqHt0t9RhBg-7SF_JbzF45-7AJRAt4CHXnYx"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants= mutableListOf<YelpRestaurant>()
        val adapter=RestaurantsAdapter(this,restaurants)

        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)


        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY", "Avocado Toast", "New York").enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did't receive valid response body from Yelp API... exiting")
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
    }
}