@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.aio_bazar.ui.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.aio_bazar.R
import com.example.aio_bazar.model.data.Ads
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.CardViewBackground
import com.example.aio_bazar.ui.theme.MainAppTheme
import com.example.aio_bazar.ui.theme.Shapes
import com.example.aio_bazar.util.CATEGORY
import com.example.aio_bazar.util.MyScreens
import com.example.aio_bazar.util.NetworkChecker
import com.example.aio_bazar.util.TAGS
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel
import org.koin.core.parameter.parametersOf

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            MainScreen()

        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    SideEffect { systemUiController.setStatusBarColor(Color.White) }
    val navigation = getNavController()
    val mainViewModel =
        getViewModel<MainViewModel>(parameters = { parametersOf(NetworkChecker(context).isInternetConnected) })
    val productDataState = mainViewModel.productList
    val adsDataState = mainViewModel.adsList


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {

        if (mainViewModel.showProgress.value) {
            LinearProgressIndicator(color = Blue, modifier = Modifier.fillMaxWidth())
        }else{
            TopToolbar(onCartClicked = {
                navigation.navigate(MyScreens.CartScreen.route)

            }, onProfileClicked = {
                navigation.navigate(MyScreens.ProfileScreen.route)
            })
            CategoryBar(CATEGORY){
                navigation.navigate(MyScreens.CategoryScreen.route+"/"+it)
            }

            ProductSubjectList(TAGS, productDataState.value, adsDataState.value){
                navigation.navigate(MyScreens.ProductScreen.route+"/"+it)
            }
        }



    }
}
//------------------------------------------

@Composable
fun ProductSubjectList(tags: List<String>, productList: List<Product>, adsList: List<Ads>,onProductItemClicked:(String)->Unit) {
    if(productList.isNotEmpty()){
        Column {
            tags.forEachIndexed { index, _ ->
                val productsFilter = productList.filter { it.tags == tags[index] }
                ProductSubject(tags[index], productsFilter.shuffled(),onProductItemClicked)
                if (adsList.size >= 2)
                    if (index == 1 || index == 2)
                        AdvertisementBox(adsList[index - 1],onProductItemClicked)
            }
        }
    }



}


//------------------------------------------------
@Composable
fun TopToolbar(onCartClicked: () -> Unit, onProfileClicked: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .background(Color.White)
            .shadow(0.dp),
        title = { Text(text = "Aio Bazaar") },
        actions = {
            IconButton(onClick = onCartClicked) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
            }
            IconButton(onClick = onProfileClicked) {
                Icon(Icons.Default.Person, contentDescription = null)
            }

        }
    )
}


//------------------------------------------------
@Composable
fun CategoryBar(categoryList: List<Pair<String, Int>>, onCategoryItemClicked: (String) -> Unit) {
        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(categoryList.size) {
                CategoryItem(category = categoryList[it],onCategoryItemClicked)
            }

        }

}

@Composable
fun CategoryItem(category: Pair<String, Int>,onCategoryItemClicked:(String)->Unit) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onCategoryItemClicked.invoke(category.first) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = Shapes.medium,
            color = CardViewBackground
        ) {
            Image(
                painter = painterResource(id = category.second),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(text = category.first, modifier = Modifier.padding(top = 4.dp), color = Color.Gray)

    }
}

//------------------------------------------------

@Composable
fun ProductSubject(tag: String, products: List<Product>,onProductItemClicked:(String)->Unit) {

        Column(modifier = Modifier.padding(top = 32.dp)) {
            Text(
                text = if (products.isNotEmpty()) tag else "",
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            ProductBar(products,onProductItemClicked)
    }

}

@Composable
fun ProductBar(products: List<Product>,onProductItemClicked:(String)->Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(products.size) {
            ProductItem(products[it],onProductItemClicked)
        }
    }
}

@Composable
fun ProductItem(product: Product,onProductItemClicked:(String)->Unit) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onProductItemClicked.invoke(product.productId) }
            .shadow(4.dp, spotColor = Blue),

        shape = Shapes.medium
    ) {
        Column {
            AsyncImage(
                model = product.imgUrl,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = product.name, style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "${product.price} Tomans", style = TextStyle(
                        fontSize = 14.sp,
                    )
                )
                Text(
                    text = "${product.soldItem} sold", style = TextStyle(
                        fontSize = 13.sp,
                        color = Color.Gray,
                    )
                )
            }
        }
    }
}


//------------------------------------------------
@Composable
fun AdvertisementBox(ads: Ads,onProductItemClicked:(String)->Unit) {
    AsyncImage(
        model = ads.imageURL,
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(
                top = 32.dp, start = 16.dp, end = 16.dp
            )
            .clip(Shapes.medium)
            .clickable { onProductItemClicked.invoke(ads.productId) },
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}



