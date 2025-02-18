import com.example.tourismchat1.data.models.TouristPlace
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface TourismApiService {
    @GET("attractions")
    suspend fun getAttractions(): List<TouristPlace>
}
