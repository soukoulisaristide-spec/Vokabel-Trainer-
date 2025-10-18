package com.example.vokabeltrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VokabelTrainerApp()
        }
    }
}

@Composable
fun VokabelTrainerApp() {
    var screen by remember { mutableStateOf("start") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF007BFF)
    ) {
        when (screen) {
            "start" -> StartScreen { screen = "quiz" }
            "quiz" -> QuizScreen { screen = "start" }
        }
    }
}

@Composable
fun StartScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.White, shape = CircleShape)
                .clickable { onStartClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start",
                fontSize = 24.sp,
                color = Color(0xFF007BFF),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun QuizScreen(onEndQuiz: () -> Unit) {
    val fragen = listOf(
        "Haus" to listOf("house", "car", "tree"),
        "Baum" to listOf("tree", "dog", "chair"),
        "Buch" to listOf("book", "window", "pen")
    )
    var index by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf<String?>(null) }

    val (deutsch, antworten) = fragen[index]
    val richtigeAntwort = antworten[0]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF007BFF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Was heißt \"$deutsch\"?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        antworten.shuffled().forEach { antwort ->
            Button(
                onClick = {
                    selected = antwort
                    if (antwort == richtigeAntwort) {
                        if (index < fragen.size - 1) index++
                        else onEndQuiz()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == antwort) Color.LightGray else Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(antwort, color = Color(0xFF007BFF))
            }
        }
    }
}
