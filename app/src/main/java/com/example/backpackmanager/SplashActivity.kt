package com.example.backpackmanager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.backpackmanager.ui.theme.BackpackManagerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BackpackManagerTheme {
                SplashScreen()
            }
        }

        lifecycleScope.launch {
            delay(1500)
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }
}


@Composable
fun SplashScreen() {
    val id = when (Random.nextInt(1, 9)) {
        1 -> R.drawable.splash_screen1
        2 -> R.drawable.splash_screen2
        3 -> R.drawable.splash_screen3
        4 -> R.drawable.splash_screen4
        5 -> R.drawable.splash_screen5
        6 -> R.drawable.splash_screen6
        7 -> R.drawable.splash_screen7
        else -> R.drawable.splash_screen8
    }

    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(id = R.string.AppName), fontSize = 45.sp, textAlign = TextAlign.Center
            , modifier = Modifier.fillMaxWidth().padding(5.dp, 70.dp)
                            .background(Color.White).border(BorderStroke(2.dp, Color(0xFFA52A2A)))
            , fontWeight = FontWeight.ExtraBold , color = Color(0xFFA52A2A))
    }
}