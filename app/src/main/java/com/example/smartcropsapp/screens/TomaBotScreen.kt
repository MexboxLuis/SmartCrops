package com.example.smartcropsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcropsapp.R
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TomaBotScreen(navController: NavHostController) {
    var messages by remember { mutableStateOf(listOf<Message>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "TomaBot") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(actions = {
                ChatInput { message ->
                    messages = messages + Message("user", message)
                    sendMessageToGeminiApi(
                        message = message,
                        onResponse = { response ->
                            messages = messages + Message("bot", response)
                        },
                        onError = { error ->
                            messages = messages + Message("bot", error)
                        }
                    )
                }
            })

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = false
            ) {
                items(messages) { message ->
                    if (message.sender == "bot") {
                        BotMessage(message.text)
                    } else {
                        UserMessage(message.text)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatInput(onSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
        )
        Button(
            onClick = {
                if (text.isNotEmpty()) {
                    onSend(text)
                    text = ""
                }
            }
        ) {
            Text("Send", color = Color.White)
        }
    }
}

@Composable
fun BotMessage(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.toma_bot),
            contentDescription = "Bot",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )
        Box(
            modifier = Modifier
                .background(Color(0xFFE0E0E0), shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
        ) {
            Text(text = text, fontSize = 16.sp, color = Color(0xFF333333))
        }
    }
}

@Composable
fun UserMessage(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFF6200EE), shape = MaterialTheme.shapes.medium)
                .padding(12.dp)
        ) {
            Text(text = text, fontSize = 16.sp, color = Color.White)
        }
    }
}

data class Message(val sender: String, val text: String)


fun sendMessageToGeminiApi(
    message: String,
    onResponse: (String) -> Unit,
    onError: (String) -> Unit
) {
    val client = OkHttpClient()
    val apiKey = "AIzaSyCiipnWNCMDZmBzprEFym4aIvyLvFVtmYs"

    val requestBody = """
        {
            "prompt": "$message"
        }
    """.trimIndent().toRequestBody("application/json; charset=utf-8".toMediaType())

    val request = Request.Builder()
        .url("https://generativelanguage.googleapis.com/v1beta2/models/gemini-1.0-flash-latest:generateText?key=$apiKey")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onError(e.message ?: "Unknown error")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val jsonResponse = JSONObject(responseBody ?: "")
                val botResponse = jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getString("output")
                onResponse(botResponse)
            } else {
                onError("Response error hehehe ${response.message}")
            }
        }
    })
}

