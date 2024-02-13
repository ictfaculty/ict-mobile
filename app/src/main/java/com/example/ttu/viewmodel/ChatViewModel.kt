package com.example.ttu.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ttu.ChatState
import com.example.ttu.FcmApi
import com.example.ttu.NotificationBody
import com.example.ttu.SendMessageDto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.IOException


class ChatViewModel:ViewModel() {

    var state by mutableStateOf(ChatState())
        private set
    private val api: FcmApi = Retrofit.Builder()
        .baseUrl("----")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    fun onRemoteTokenChange(newToken: String) {
        state = state.copy(
            remoteToken = newToken
        )
    }

    fun onSubmitRemoteToken() {
        state = state.copy(
            isEnteringToken = false
        )
    }


    fun onMessageChange(message: String) {
        state = state.copy(
            messageText = message
        )
    }

    fun sendMessage(isBroadcast: Boolean) {
        viewModelScope.launch {
            val messageDto = (if(isBroadcast) null else state.remoteToken)?.let {
                SendMessageDto(
                    to = it,
                    notification = NotificationBody(
                        title = "New message",
                        body = state.messageText
                    )
                )
            }

            try {
                if (isBroadcast) {
                    if (messageDto != null) {
                        api.broadcast(messageDto)
                    }
                } else {
                    if (messageDto != null) {
                        api.sendMessage(messageDto)
                    }
                }

                state = state.copy(
                    messageText = ""
                )

            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}