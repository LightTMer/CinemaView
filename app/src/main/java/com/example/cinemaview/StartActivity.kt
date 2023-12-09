package com.example.cinemaview


import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
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
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter


class StartActivity : AppCompatActivity() {
    lateinit var composeView: ComposeView
    @SuppressLint("MissingInflatedId")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        composeView = findViewById(R.id.compose_view)
        composeView.setContent {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {

//                StartText()
                MovieDetailsScreen()
                MovieDetailsScreen2()

             StartButton()

            }
        }
        data class Movie(
            val title: String,
            val year: Int,
            val description: String,
            val rating: Float,
            val posterResId: Int
        )

    }
    @Composable
    fun ImageList(images: List<String>) {
        LazyColumn {
            items(images) { imageUrl ->
                ImageItem(imageUrl = imageUrl)
            }
        }
    }
    @Composable
    fun ImageItem(imageUrl: String) {
        val painter: Painter = painterResource(id = R.drawable.alexxx) // Placeholder image while loading

         Image(
             painter =painter,
//             painter = rememberImagePainter(data = imageUrl),
             contentDescription = null,
             modifier = Modifier
                 .fillMaxWidth()
                 .height(200.dp)
         )
    }
    @Composable
    fun ImageLoader(item: String) {

        val url = "https://www.ebookfrenzy.com/book_examples/car_logos/" + item.
        substringBefore(" ") + "_logo.png"

        Image(
            painter = rememberImagePainter(url),
            contentDescription = "car image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(75.dp)
        )
    }

    @Composable
    fun MovieDetailsScreen() {
//        В mainactivity я прописал класс data Movie, его также можешь использовать для удобства вывода информации, как делали в лабах ранее
//        movie: Movie - значение передаем в функцию

        val configuration = LocalConfiguration.current
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {


            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
//                    text = movie.title,
                        text = "Название",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        color = Color.Black,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                    Text(text = "Year: ${movie.year}")
//                    Text(text = "Rating: ${movie.rating}")
                        Text(text = "Год: ", modifier = Modifier
                            .offset(x = 50.dp, y= 0.dp))
                        Text(text = "Рейтинг:", modifier = Modifier
                            .offset(x = -50.dp, y= 0.dp))
                    }
                }


                //Вместо этого блока, нужно подставить функцию для вывода картинки по ссылке ( ImageList или ImageLoader тут на выбор для твоего удобства реализации)
                item {
                    Image(
//                    painter = painterResource(id = movie.posterResId),
                        painter = painterResource(id = R.drawable.alexxx),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = 70.dp, y= 0.dp)
                            .width(250.dp)
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Text(
//                    text = movie.description,
                        text = "описаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописание",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
    @Composable
    fun StartButton() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp))
        {
            ElevatedButton(
                onClick = {
                } ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 25.dp, y = 625.dp)
                    .width(200.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "О приложении"
                    )
                }

            )
            ElevatedButton(
                onClick = {
                } ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 50.dp, y = 625.dp)
                    .width(200.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "О приложении"
                    )
                }

            )
        }
        }
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

    @Composable
    fun MovieDetailsScreen2() {
        val configuration = LocalConfiguration.current
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {


            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
//                    painter = painterResource(id = movie.posterResId),
                        painter = painterResource(id = R.drawable.alexxx),
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(200.dp)
                            .padding(end = 16.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
//                        text = movie.title,
                            text = "Название",
                            modifier = Modifier.padding(bottom = 120.dp),
                            color = Color.Black,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
//                text = movie.description,
                            text = "описаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописаниеописание",
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = 5.dp, y=-100.dp )
//                                .padding(16.dp)
                        )

                    }
                }
                Text(text = "Year: movie.year")
                Text(text = "Rating:movie.rating")


            }
            ElevatedButton(
                onClick = {
                } ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = -200.dp, y = 150.dp)
                    .width(200.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "О приложении"
                    )
                }

            )
            ElevatedButton(
                onClick = {
                } ,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .offset(x = 200.dp, y = 150.dp)
                    .width(200.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "О приложении"
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

    }}

