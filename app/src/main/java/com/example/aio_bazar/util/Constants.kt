package com.example.aio_bazar.util

import com.example.aio_bazar.R
import com.example.aio_bazar.model.data.Product


const val KEY_PRODUCT_ARG="productsArg"
const val KEY_CATEGORY_ARG="categoryArgs"


const val BASE_URL = "https://dunijet.ir/Projects/DuniBazaar/"
const val VALUE_SUCCESS="success"


val EMPTY_PRODUCT=Product("","","","","","","","","")
val CATEGORY = listOf(
    Pair("Backpack", R.drawable.ic_cat_backpack),
    Pair("Handbag", R.drawable.ic_cat_handbag),
    Pair("Shopping", R.drawable.ic_cat_shopping),
    Pair("Tote", R.drawable.ic_cat_tote),
    Pair("Satchel", R.drawable.ic_cat_satchel),
    Pair("Clutch", R.drawable.ic_cat_clutch),
    Pair("Wallet", R.drawable.ic_cat_wallet),
    Pair("Sling", R.drawable.ic_cat_sling),
    Pair("Bucket", R.drawable.ic_cat_bucket),
    Pair("Quilted", R.drawable.ic_cat_quilted)
)
val TAGS = listOf(
    "Newest",
    "Best Sellers",
    "Most Visited",
    "Highest Quality",
)

