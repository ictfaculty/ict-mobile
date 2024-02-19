package com.example.ttu.view

import ApiService
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttu.Adapter.NewsAdapter
import com.example.ttu.databinding.FragmentNewsBinding
import com.example.ttu.model.JsonNews
import com.example.ttu.model.News
import com.example.ttu.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
    private lateinit var mainApi: ApiService
    private lateinit var binding: FragmentNewsBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter: NewsAdapter = NewsAdapter()
        binding.recyclerNews.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerNews.adapter = adapter

        adapter.notifyDataSetChanged()

        mainApi = RetrofitClient.getClient().create(ApiService::class.java)
        val call: Call<JsonNews> = mainApi.getAllCats()
        call.enqueue(object : Callback<JsonNews> {
            override fun onResponse(call: Call<JsonNews>, response: Response<JsonNews>) {
                if (response.isSuccessful) {
                    val news: JsonNews? = response.body()
                    adapter.submitList(news?.data)
                } else {
                    Toast.makeText(binding.root.context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonNews>, t: Throwable) {
                Toast.makeText(binding.root.context, "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }
}

