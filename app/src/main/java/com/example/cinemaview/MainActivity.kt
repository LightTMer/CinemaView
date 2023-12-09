package com.example.cinemaview

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    lateinit var composeView: ComposeView
    private lateinit var appDatabase: AppDatabase
//    lateinit var openButton: Button

    val brush = Brush.linearGradient(colors = listOf(Color.Blue, Color.Green) )
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Thread.sleep(400)


        setContentView(R.layout.activity_main)

        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
        checkLocalData()

        val constraintLayout = findViewById(R.id.layout) as LinearLayout
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        composeView = findViewById(R.id.compose_view)
        composeView.setContent {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Greeting("CinemaView")
                StartButton {

                }
            }
        }
    }

    private fun fetchDataFromServer() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Accept-Encoding", "gzip")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://159.223.230.93:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = movieService.getMovies().execute()
                if (response.isSuccessful) {
                    val movies = response.body() ?: emptyList()
                    saveDataToLocalDatabase(movies)
                    withContext(Dispatchers.Main) {
                        displayMovies(movies)
                    }
                } else {
                    showError()
                }
            } catch (e: Exception) {
                showError()
            }
        }
    }

    private fun displayMovies(movies: List<MovieEntity>) {
        // Код отображения данных
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Не удалось загрузить", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDataToLocalDatabase(movies: List<MovieEntity>) {
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.movieDao().insertMovies(movies)
        }
    }

    private fun checkLocalData() {
        GlobalScope.launch(Dispatchers.IO) {
            val movies: List<MovieEntity> = appDatabase.movieDao().getAllMovies()
            withContext(Dispatchers.Main) {
                if (movies.isNotEmpty()) {
                    displayMovies(movies)
                } else {
                    fetchDataFromServer()
                }
            }
        }
    }

    @Composable
    fun Greeting(text: String) {
        val configuration = LocalConfiguration.current
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        Text(
            buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        brush = brush, alpha = .5f, fontSize = 40.sp,
                    )
                ) {

                    append("$text")
                }
                withStyle(
                    SpanStyle(
                        brush = brush, alpha = 1f, fontSize = 40.sp
                    )
                ) {
                    append("\uD83C\uDFAC")
                }
            },
//            modifier = Modifier.clip(CircleShape).background(color = Color.White)
            )}
        else if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Text(

                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            brush = brush, alpha = .5f, fontSize = 40.sp,
                            )
                    ) {
                        append("$text")
                    }
                    withStyle(
                        SpanStyle(
                            brush = brush, alpha = 1f, fontSize = 40.sp
                        )
                    ) {
                        append("\uD83C\uDFAC")
                    }
                },modifier = Modifier.offset(x = 0.dp, y = -75.dp))
        }

    }
    @Composable
    fun StartButton(onClick: () -> Unit) {

        val configuration = LocalConfiguration.current
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        Box(
            modifier = Modifier.fillMaxSize()
        ){

        }
        ElevatedButton(
            onClick = { onClick()
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Добро пожаловать в мир кино!", Toast.LENGTH_SHORT).show()} ,
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .offset(x = 0.dp, y = 60.dp)
                .width(200.dp)

        ) {
            Text("Начать")
        }
        ElevatedButton(
            onClick = { onClick()
                val intent = Intent(this, AboutApp::class.java)
                startActivity(intent)
            } ,
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .offset(x = 0.dp, y = 120.dp)
                .width(200.dp)

        ) {
            Text("О приложении")
        }
        ElevatedButton(
            onClick = { onClick()
                      finish()
                      } ,
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .offset(x = 0.dp, y = 180.dp)
                .width(200.dp)

        ) {
            Text("Выйти")
        }

    }

        else if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Box(
                modifier = Modifier.fillMaxSize()
            ){

            }
            ElevatedButton(
                onClick = { onClick()
                    val intent = Intent(this, StartActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Добро пожаловать в мир кино!", Toast.LENGTH_SHORT).show()} ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 0.dp, y = -10.dp)
                    .width(200.dp)

            ) {
                Text("Начать")
            }
            ElevatedButton(
                onClick = { onClick()
                    val intent = Intent(this, AboutApp::class.java)
                    startActivity(intent)
                } ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 0.dp, y = 50.dp)
                    .width(200.dp)

            ) {
                Text("О приложении")
            }
            ElevatedButton(
                onClick = { onClick()
                    finish()
                } ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 0.dp, y = 110.dp)
                    .width(200.dp)

            ) {
                Text("Выйти")
            }

        }
    }
}


