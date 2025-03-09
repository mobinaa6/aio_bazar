package com.example.aio_bazar.ui.features.signIn

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aio_bazar.R
import com.example.aio_bazar.ui.theme.BackgroundMain
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.MainAppTheme
import com.example.aio_bazar.ui.theme.Shapes
import com.example.aio_bazar.util.MyScreens
import com.example.aio_bazar.util.NetworkChecker
import com.example.aio_bazar.util.VALUE_SUCCESS
import com.example.aio_bazar.widget.textFiled.MainTextField
import com.example.aio_bazar.widget.textFiled.PasswordTextField
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {

    MainAppTheme {
        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            SignInScreen()
        }
    }
}

@Composable
fun SignInScreen() {
    val signInViewmodel = getViewModel<SignInViewModel>()
    val navigation = getNavController()
    val context= LocalContext.current
    Box {
        Box(
            modifier = Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth()
                .background(Blue)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxHeight(.8f)
        ) {
            IconApp()
            MainCardView(
                viewModel = signInViewmodel,
                navigation = navigation
            ) {
                signInViewmodel.signInUser {
                    if(it== VALUE_SUCCESS){
                        navigation.navigate(MyScreens.MainScreen.route){
                            popUpTo(MyScreens.MainScreen.route){
                                inclusive=true
                            }
                        }
                    }else{
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }
}

@Composable
fun IconApp() {
    Surface(
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
    ) {
        Image(
            modifier = Modifier.padding(14.dp),
            painter = painterResource(id = R.drawable.ic_icon_app), contentDescription = null
        )
    }
}


@Composable
fun MainCardView(navigation: NavController, viewModel: SignInViewModel, signInEvent: () -> Unit) {
    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")
    val  context= LocalContext.current

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        shape = Shapes.medium
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign In",
                modifier = Modifier.padding(vertical = 18.dp),
                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )

            MainTextField(
                edtValue = email.value,
                icon = R.drawable.ic_email,
                hint = "Email",
                isEmail = true
            ) { viewModel.email.value = it }
            PasswordTextField(
                edtValue = password.value,
                icon = R.drawable.ic_password,
                hint = "Password",
                isLastTextField = false,
            ) { viewModel.password.value = it }
            Button(
                onClick = {
                    if ( email.value.isNotEmpty() && password.value.isNotEmpty() ) {
                            if (password.value.length >= 8) {
                                if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                                    if(NetworkChecker(context).isInternetConnected){
                                        signInEvent.invoke()

                                    }else{
                                        Toast.makeText(context, "please connect to the internet", Toast.LENGTH_SHORT).show()
                                    }

                                } else {
                                    Toast.makeText(
                                        context,
                                        "email format is not true!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    "password characters should be than 8!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }




                    } else {
                        Toast.makeText(context, "please write data first", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            ) {
                Text(text = "Log In", modifier = Modifier.padding(8.dp))

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(18.dp)
            ) {
                Text(text = "Don't have an account")
                TextButton(onClick = {
                    navigation.navigate(MyScreens.SignUpScreen.route) {
                        popUpTo(MyScreens.SignInScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Register Here", color = Blue)

                }
            }


        }

    }
}



