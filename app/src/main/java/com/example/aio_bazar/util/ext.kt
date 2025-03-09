package com.example.aio_bazar.util

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler


val coroutineExceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->

    Log.v("error","error -> ${throwable.message}")
}