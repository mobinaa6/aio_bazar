@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.aio_bazar.ui.features.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.Shapes
import com.example.aio_bazar.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel


@Composable
fun CategoryScreen(categoryName: String) {
    val categoryViewModel = getViewModel<CategoryViewModel>()
    categoryViewModel.loadDataByCategory(categoryName)
    val navigation = getNavController()
    val productsByCategory = categoryViewModel.productList
    Column(modifier = Modifier.fillMaxSize()) {
        CategoryToolBar(categoryName)
        CategoryList(productsByCategory.value) {
            navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
        }

    }
}

@Composable
fun CategoryList(productList: List<Product>, onProductClicked: (String) -> Unit) {
    if (productList.isNotEmpty()) {
        LazyColumn(modifier=Modifier.fillMaxSize()) {
            items(productList.size) {
                CategoryItem(product = productList[it],onProductClicked)
            }

        }
    }

}


@Composable
fun CategoryItem(product: Product, onProductClicked: (String) -> Unit) {
    Card(
        shape = Shapes.large,
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
           .clickable { onProductClicked.invoke(product.productId) }
            .shadow(4.dp, spotColor = Blue)
    ) {
        AsyncImage(
            model = product.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp, start = 4.dp),
            text = product.name,
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.price + "Tomans",
            )
            Surface(
                shape = Shapes.large,
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 8.dp)
                    .align(Alignment.Bottom),
                color = Blue
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = product.soldItem + "old",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )
            }

        }


    }
}

@ExperimentalMaterial3Api
@Composable
fun CategoryToolBar(categoryName: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(Color.White),
        title = {
        Text(
            textAlign = TextAlign.Center,
            text = categoryName,
            modifier = Modifier
                .fillMaxWidth()
        )
    })


}
