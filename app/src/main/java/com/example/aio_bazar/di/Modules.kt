package com.example.aio_bazar.di

import android.content.Context
import androidx.room.Room
import com.example.aio_bazar.model.db.AppDatabase
import com.example.aio_bazar.model.net.createApiService
import com.example.aio_bazar.model.repository.User.UserRepository
import com.example.aio_bazar.model.repository.User.UserRepositoryImpl
import com.example.aio_bazar.model.repository.comment.CommentRepository
import com.example.aio_bazar.model.repository.comment.CommentRepositoryImpl
import com.example.aio_bazar.model.repository.product.ProductRepository
import com.example.aio_bazar.model.repository.product.ProductRepositoryImpl
import com.example.aio_bazar.ui.features.category.CategoryViewModel
import com.example.aio_bazar.ui.features.main.MainViewModel
import com.example.aio_bazar.ui.features.product.ProductViewModel
import com.example.aio_bazar.ui.features.signIn.SignInViewModel
import com.example.aio_bazar.ui.features.singUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val myModules = module {

    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }
    single { createApiService() }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database.db").build()
    }

    //repository
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get<AppDatabase>().productDao()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }


    //view model
    viewModel<SignUpViewModel> { SignUpViewModel(get()) }
    viewModel<SignInViewModel> { SignInViewModel(get()) }
    viewModel<MainViewModel> { (isNetConnected: Boolean) -> MainViewModel(get(), isNetConnected) }
    viewModel { CategoryViewModel(get()) }
    viewModel<ProductViewModel> { ProductViewModel(get(), get()) }
}