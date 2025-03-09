
package com.example.aio_bazar.ui.features.product

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.aio_bazar.R
import com.example.aio_bazar.model.data.Comment
import com.example.aio_bazar.model.data.Product
import com.example.aio_bazar.ui.theme.Blue
import com.example.aio_bazar.ui.theme.MainAppTheme
import com.example.aio_bazar.ui.theme.Shapes
import com.example.aio_bazar.util.MyScreens
import com.example.aio_bazar.util.NetworkChecker
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.viewmodel.getViewModel

@Preview(showBackground = true)
@Composable
fun ProductScreenPreview() {
    MainAppTheme {
        Surface(modifier = Modifier.wrapContentSize(), color = Color.White) {
            ProductScreen(productId = "")
//            CommentBox()
        }
    }
}

@Composable
fun ProductScreen(productId: String) {
    val context = LocalContext.current
    val navigation = getNavController()
    val productViewModel = getViewModel<ProductViewModel>()
    productViewModel.loadData(productId, NetworkChecker(context).isInternetConnected)
    val listState = rememberLazyListState()
    val product = productViewModel.productState.value
    val commentList = productViewModel.commentListState.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            item {
                ProductToolbar(
                    productName = "Details",
                    badgeNumber = 2,
                    onBackClicked = {
                        navigation.popBackStack()
                    }, onCartClicked = {
                        if (NetworkChecker(context).isInternetConnected) {
                            navigation.navigate(MyScreens.CartScreen.route)
                        } else {
                            Toast.makeText(
                                context,
                                "please connect to internet first...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
            item {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ProductDesign(product = product) {
                        navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
                    }
                    Divider(
                        modifier = Modifier.padding(vertical = 14.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                    ProductDetail(product, commentList.size.toString())
                    Divider(
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )
                }
            }
            item { AddComment(commentList) {
                productViewModel.addNewComment(productId,it) { it ->
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            } }
            items(commentList.size) { index ->
                CommentBox(comment = commentList[index])
            }
        }
        AddToCart()
    }
}

@Composable
fun AddComment(commentList: List<Comment>, addNewComment: (String) -> Unit) {
    val showNewCommentDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (commentList.isNotEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Comments", fontSize = 20.sp, fontWeight = FontWeight.Medium)
            TextButton(
                onClick = {
                    if (NetworkChecker(context).isInternetConnected) {
                        showNewCommentDialog.value = true
                    } else {
                        Toast.makeText(
                            context,
                            "connect to internet to add comment...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                Text(text = "Add New Comment")
            }
        }

    } else {
        TextButton(
            onClick = {
                if (NetworkChecker(context).isInternetConnected) {
                    showNewCommentDialog.value = true
                } else {
                    Toast.makeText(
                        context,
                        "connect to internet to add comment...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            Text(text = "Add New Comment")
        }
    }
    if (showNewCommentDialog.value) {
        AddNewCommentDialog(
            onDismiss = { showNewCommentDialog.value = false },
            onPositiveClick = {
                addNewComment.invoke(it)

            }
        )
    }


}

@Composable
fun AddNewCommentDialog(
    onDismiss: () -> Unit,
    onPositiveClick: (String) -> Unit
) {
    val context = LocalContext.current
    val userComment = remember {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .shadow(8.dp)
                .clip(shape = Shapes.medium)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =
                Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Write your comment",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))
                MainTextField(edtValue = userComment.value, hint = "write Something") {
                    userComment.value=it

                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {

                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")

                    }
                    TextButton(onClick = {
                        if (userComment.value.isNotEmpty() && userComment.value.isNotBlank()) {
                            if (NetworkChecker(context).isInternetConnected) {
                                onPositiveClick.invoke(userComment.value)
                                onDismiss.invoke()
                            } else {
                                Toast.makeText(
                                    context,
                                    "connect to the internet first first...",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }


                        } else {
                            Toast.makeText(context, "please write first...", Toast.LENGTH_SHORT)
                                .show()

                        }

                    }) {
                        Text(text = "Send")

                    }

                }

            }
        }
    }
}

@Composable
fun MainTextField(edtValue: String, hint: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        value = edtValue,
        onValueChange = onValueChanged,

        placeholder = { Text(text = "Write Something...") },
        label = { Text(text = hint) },
        singleLine = false,
        maxLines = 2,
        modifier = Modifier.fillMaxWidth(.9f),
        shape = Shapes.medium
    )
}

@Composable
fun CommentBox(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .shadow(0.dp),
        colors = CardDefaults.cardColors(Color.White),

        border = BorderStroke(1.dp, Color.LightGray),
        shape = Shapes.large
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = comment.userEmail, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = comment.text,
                fontSize = 14.sp,
            )

        }

    }
}

@Composable
fun ProductDetail(product: Product, commentNumber: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column()
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_details_comment),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )

                Text(
                    text = "$commentNumber Comments",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_details_material),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )

                Text(
                    text = product.material,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_details_sold),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )

                Text(
                    text = "${product.soldItem} Sold",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )

            }
        }
        Surface(
            modifier = Modifier.clip(shape = Shapes.large),
            color = Blue
        ) {
            Text(
                text = product.tags, modifier = Modifier.padding(6.dp),
                style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
fun ProductDesign(product: Product, onCategoryClicked: (String) -> Unit) {
    Column {
        AsyncImage(
            model = product.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(Shapes.medium)
        )
        Text(
            modifier = Modifier.padding(top = 14.dp),
            text = product.name,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = product.detailText,
            textAlign = TextAlign.Justify,
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = TextUnit(24f, TextUnitType.Sp)
            )
        )
        TextButton(onClick = { onCategoryClicked.invoke(product.category) }) {
            Text(text = "#${product.category}", style = TextStyle(fontSize = 13.sp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductToolbar(
    productName: String,
    badgeNumber: Int,
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        modifier = Modifier
            .shadow(elevation = 2.dp, spotColor = Blue)
            .fillMaxWidth(),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp),
                textAlign = TextAlign.Center,
                text = productName
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {

            IconButton(onClick = { onCartClicked.invoke() }, modifier = Modifier.size(80.dp)) {
                if (badgeNumber == 0)
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                else BadgedBox(badge = { Badge { Text(text = badgeNumber.toString()) } }) {

                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                }
            }
        }
    )
}

@Composable
fun AddToCart(
) {



}
