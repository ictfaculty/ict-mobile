package com.example.ttu.view

import ApiService
import RetrofitClient
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ttu.Not
import com.example.ttu.model.AuthRequest
import com.example.ttu.viewmodel.LoginViewModel
import com.example.ttu.R
import com.example.ttu.databinding.LoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    private lateinit var binding: LoginBinding
    private lateinit var mainApi: ApiService
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()

        binding.buttonLogin.setOnClickListener {
            auth(
                AuthRequest(
                    binding.eLogin.text.toString().trim(),
                    binding.ePassword.text.toString().trim()
                )
            )

        }
        viewModel.token.observe(viewLifecycleOwner) { token ->
            if (!token.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_loginFragment_to_newsFragment)
            }
        }
    }

        private fun initRetrofit() {
        mainApi = RetrofitClient.getClient().create(ApiService::class.java)
    }

    private fun auth(authRequest: AuthRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fcmToken = (requireActivity().application as Not).getFcmToken()
                val response = mainApi.auth(authRequest)
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user!=null) {
                        viewModel.token.postValue(user.data.access_token)
                    }
                } else {
                    Log.e("TAG", "Auth failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Auth failed: ${e.message}")
            }
        }
    }
}
