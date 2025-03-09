package com.example.aio_bazar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aio_bazar.di.myModules
import com.example.aio_bazar.model.repository.TokenInMemory
import com.example.aio_bazar.model.repository.User.UserRepository
import com.example.aio_bazar.ui.features.IntroScreen
import com.example.aio_bazar.ui.features.category.CategoryScreen
import com.example.aio_bazar.ui.features.main.MainScreen
import com.example.aio_bazar.ui.features.product.ProductScreen
import com.example.aio_bazar.ui.features.signIn.SignInScreen
import com.example.aio_bazar.ui.features.singUp.SignUpScreen
import com.example.aio_bazar.ui.theme.BackgroundMain
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.MainAppTheme
import com.example.aio_bazar.util.KEY_CATEGORY_ARG
import com.example.aio_bazar.util.KEY_PRODUCT_ARG
import com.example.aio_bazar.util.MyScreens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.get
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Koin(
                appDeclaration = {
                    androidContext(this@MainActivity)
                    modules(myModules)
                }
            ) {
                MainAppTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = BackgroundMain) {
                        val userRepository: UserRepository = get()
                        userRepository.loadToken()
                        ShopUi()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundMain
        ) {

            ShopUi()


        }
    }
}

@Composable
fun ShopUi() {
    val navController = rememberNavController()
    KoinNavHost(navController = navController, startDestination = MyScreens.MainScreen.route) {
        composable(MyScreens.MainScreen.route) {
            if (TokenInMemory.token != null) {
                MainScreen()
            } else {
                IntroScreen()
            }
        }
        composable(MyScreens.ProductScreen.route + "/" + "{$KEY_PRODUCT_ARG}",
            arguments = listOf(
                navArgument(KEY_PRODUCT_ARG) { type = NavType.StringType }
            )) {
            ProductScreen(it.arguments!!.getString(KEY_PRODUCT_ARG, "null"))
        }

        composable(
            MyScreens.CategoryScreen.route + "/" + "{$KEY_CATEGORY_ARG}",
            arguments = listOf(navArgument(KEY_CATEGORY_ARG) { type = NavType.StringType })
        ) {
            CategoryScreen(it.arguments!!.getString(KEY_CATEGORY_ARG, "null"))
        }

        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(MyScreens.CartScreen.route) {
            CartScreen()
        }

        composable(MyScreens.SignUpScreen.route) {
            SignUpScreen()
        }

        composable(MyScreens.SignInScreen.route) {

            SignInScreen()
        }


    }

}

@Composable
fun ProfileScreen() {
}

@Composable
fun CartScreen() {
}




