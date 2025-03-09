package com.example.aio_bazar.util

sealed class MyScreens(val route: String) {

    data object MainScreen : MyScreens("mainScreen")
    data object ProfileScreen : MyScreens("profileScreen")
    data object SignInScreen : MyScreens("signInScreen")
    data object SignUpScreen : MyScreens("signUpScreen")
    data object ProductScreen : MyScreens("productScreen")
    data object CategoryScreen : MyScreens("categoryScreen")
    data object CartScreen : MyScreens("cartScreen")

}