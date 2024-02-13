import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://dummyjson.com"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

//val interceptor = HttpLoggingInterceptor()
//interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//val client = OkHttpClient.Builder()
//    .addInterceptor(interceptor)
//    .build()
//
//val retrofit = Retrofit.Builder()
//    .baseUrl("https://dummyjson.com").client(client)
//    .addConverterFactory(GsonConverterFactory.create()).build()