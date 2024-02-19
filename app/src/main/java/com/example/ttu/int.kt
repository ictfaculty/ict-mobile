
import com.example.ttu.model.AuthRequest
import com.example.ttu.model.JsonNews
import com.example.ttu.model.JsonUser
import com.example.ttu.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): Response<JsonUser>

    @GET("news?page=1&count=100")
    fun getAllCats(): Call<JsonNews>
}
