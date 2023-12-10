package com.example.cinemaview

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StartActivity : AppCompatActivity() {
    private lateinit var appDatabase: AppDatabase
    lateinit var composeView: ComposeView

    @SuppressLint("MissingInflatedId")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
        GlobalScope.launch(Dispatchers.IO) {
            var movies: List<MovieEntity> = appDatabase.movieDao().getAllMovies()
            if (!movies.isNotEmpty()) {
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


                try {
                    val response = movieService.getMovies().execute()
                    if (response.isSuccessful) {
                        val films = response.body() ?: emptyList()
                        appDatabase.movieDao().insertMovies(films)
                        movies = appDatabase.movieDao().getAllMovies()
                    } else {
                        showError()
                    }
                } catch (e: Exception) {
                    showError()
                }
            }
            runOnUiThread {
                composeView = findViewById(R.id.compose_view)
                composeView.setContent {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {

                        MovieDetailsScreen(movies = movies)

                        StartButton()

                    }
                }
            }
        }
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Не удалось загрузить", Toast.LENGTH_SHORT).show()
        }
    }

    @Composable
    fun MovieDetailsScreen(movies: List<MovieEntity>) {
        val configuration = LocalConfiguration.current
        var currentIndex by rememberSaveable { mutableStateOf(0) }
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = movies[currentIndex].title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        color = Color.Black,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    val painter: Painter =
                        rememberImagePainter(data = movies[currentIndex].posterUrl)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .width(300.dp)
                            .height(300.dp)
                            .offset(x = 50.dp)
                            .padding(end = 5.dp),
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Год: ${movies[currentIndex].releaseDate} ", modifier = Modifier
                                .offset(x = 50.dp, y = 0.dp)
                        )
                        Text(
                            text = "Рейтинг: ${movies[currentIndex].rating}", modifier = Modifier
                                .offset(x = -50.dp, y = 0.dp)
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ElevatedButton(
                                onClick = {
                                    currentIndex = (currentIndex - 1 + movies.size) % movies.size
                                },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(50.dp)
                                    .offset(x = 25.dp, y = 240.dp),
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowLeft,
                                        contentDescription = "Влево"
                                    )
                                }
                            )
                            ElevatedButton(
                                onClick = {
                                    currentIndex = (currentIndex + 1) % movies.size
                                },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(50.dp)
                                    .offset(x = 50.dp, y = 240.dp),
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowRight,
                                        contentDescription = "Вправо"
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = movies[currentIndex].overview,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    )
                }
            }
        } else if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val painter: Painter =
                        rememberImagePainter(data = movies[currentIndex].posterUrl)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(200.dp)
                            .padding(end = 16.dp)
                            .offset(y = 10.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
                            text = movies[currentIndex].title,
                            modifier = Modifier.padding(bottom = 50.dp),
                            color = Color.Black,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = movies[currentIndex].overview,
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = 5.dp, y = -30.dp)
                        )

                    }
                }

                Text(
                    text = "Год: ${movies[currentIndex].releaseDate}",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .offset(y = 5.dp),
                    fontSize = 18.sp,

                    )
                Text(
                    text = "Рейтинг: ${movies[currentIndex].rating}",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .offset(y = 5.dp),
                    fontSize = 18.sp,
                )
            }
            ElevatedButton(
                onClick = {
                    currentIndex = (currentIndex - 1 + movies.size) % movies.size
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = -200.dp, y = 150.dp)
                    .width(200.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Влево"
                    )
                }

            )
            ElevatedButton(
                onClick = {
                    currentIndex = (currentIndex + 1) % movies.size
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 200.dp, y = 150.dp)
                    .width(200.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Вправо"
                    )
                }

            )
            ElevatedButton(
                onClick = { onBackPressed() },
                modifier = Modifier

                    .height(50.dp)
                    .offset(x = 0.dp, y = 150.dp)
                    .width(200.dp)

            ) {
                androidx.compose.material3.Text("Назад")
            }

        }
    }

    @Composable
    fun StartButton() {

        ElevatedButton(
            onClick = { onBackPressed() },
            modifier = Modifier

                .height(50.dp)
                .offset(x = 0.dp, y = 340.dp)
                .width(200.dp)

        ) {
            androidx.compose.material3.Text("Назад")
        }
    }
}