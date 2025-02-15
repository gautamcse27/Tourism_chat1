

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8002/"  // Replace with your actual API base URL

    val api: TourismApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // Set the base URL of your API
            .addConverterFactory(GsonConverterFactory.create())  // Converts JSON response to Kotlin objects
            .build()
            .create(TourismApiService::class.java)  // Create an instance of ApiService
    }
}
