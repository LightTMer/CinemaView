package com.example.cinemaview

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class AboutApp : AppCompatActivity() {
    lateinit var composeView: ComposeView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)


        composeView = findViewById(R.id.compose_view)
        composeView.setContent {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                InformationScreen()
            }
        }
        val constraintLayout = findViewById(R.id.layout) as LinearLayout
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()
    }

    @Composable
    fun InformationScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Наша команда")

            Row(
                modifier = Modifier.padding(16.dp)
            )
            {
                    Image(
                        painter = painterResource(id = R.drawable.alexx),
                        contentDescription = "Image 1",
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
//                            .offset(x=-5.dp)
//                        v.width(200.dp).height(200.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ilya),
                        contentDescription = "Image 2",
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
//                            .offset(x=5.dp)

                    )
            }
            Row(
                modifier = Modifier.padding(16.dp),
            )
            {
                Text(
                    modifier = Modifier

                        .padding(horizontal = 5.dp)
                        .wrapContentWidth(Alignment.Start)
                        .offset(x=-21.dp),
                    text = "Ершов Александр"
                )
                Text(
                    modifier = Modifier

                        .padding(horizontal = 5.dp)
                        .wrapContentWidth(Alignment.End)
                        .offset(x=7.dp),
                    text = "Потехин Илья"
                )
            }

            Text(modifier = Modifier.padding(vertical = 30.dp), text ="CinemaView - это приложение, которое представляет собой сборник информации о разных фильмах, c их описанием и мировым рейтингом. По сути, это своего рода энциклопедия в мире кино!")
            ElevatedButton(

                onClick = { },
                modifier = Modifier

                    .height(50.dp)
                    .width(200.dp)

            ) {
                Text("Перейти на Github")
            }
            ElevatedButton(
                onClick = { onBackPressed() },
                modifier = Modifier

                    .height(50.dp)
                    .offset(x = 0.dp, y = 120.dp)
                    .width(200.dp)

            ) {
                Text("Назад")
            }
        }
    }
}