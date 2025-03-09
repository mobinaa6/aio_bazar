package com.example.aio_bazar.ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.aio_bazar.R
import com.example.aio_bazar.ui.theme.BackgroundMain
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.MainAppTheme
import com.example.aio_bazar.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import okhttp3.internal.wait


@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    MainAppTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            IntroScreen()
        }
    }
}

@Composable
fun IntroScreen() {
    val navigation= getNavController()
    Box {
        Image(
            painter = painterResource(id = R.drawable.img_intro),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(.78f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(.7f),

                onClick = {
                    navigation.navigate(MyScreens.SignUpScreen.route) }) {

                Text(text = "Sign Up")

            }
            Button(
                modifier = Modifier.fillMaxWidth(.7f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),

                onClick = {
                    navigation.navigate(MyScreens.SignInScreen.route)
                }) {

                Text(text = "Sign In", color = Blue)

            }

        }
    }
}