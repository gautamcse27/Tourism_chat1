import com.example.tourismchat1.data.models.TouristPlace
import retrofit2.http.GET
import retrofit2.http.Path

interface TourismApiService {
    @GET("attractions") // ✅ Ensure this endpoint matches your backend
    suspend fun getAttractions(): List<TouristPlace>
    @GET("attractions/{id}")
    suspend fun getAttractionDetails(@Path("id") id: Int): TouristPlace
}
