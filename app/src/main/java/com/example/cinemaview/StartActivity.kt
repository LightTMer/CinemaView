package com.example.cinemaview


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


import androidx.compose.material3.ElevatedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp


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

             StartButton()
            }
        }

    }

    @Composable
    fun StartButton() {
        ElevatedButton(
            onClick = { onBackPressed() },
            modifier = Modifier

                .height(50.dp)
                .offset(x = 0.dp, y = 300.dp)
                .width(200.dp)

        ) {
            androidx.compose.material3.Text("Назад")
        }
    }
    @Composable
    fun MyScreenContent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Magenta)
                ) {
                    androidx.compose.material3.Text(text = "Button 1")
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Magenta)
                ) {
                    androidx.compose.material3.Text(text = "Button 2")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.my_image),
//                    contentDescription = "My Image",
//                    modifier = Modifier.size(100.dp)
//                )
                androidx.compose.material3.Text(
                    text = "Some text",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            androidx.compose.material3.Text(
                text = "Short description",
                modifier = Modifier.fillMaxWidth()
            )
        }

}
}