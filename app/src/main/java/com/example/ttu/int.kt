import com.example.ttu.model.AuthRequest
import com.example.ttu.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): Response<User>
}
