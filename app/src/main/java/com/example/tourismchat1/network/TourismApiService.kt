import com.example.tourismchat1.data.models.TouristPlace
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface TourismApiService {
    @GET("http://10.0.2.2:8002/")
    suspend fun getAttractions(): List<TouristPlace>
}
