package io.github.sooakim.sfoide.view.di

import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.github.sooakim.sfoide.view.detail.UserDetailViewModel
import io.github.sooakim.sfoide.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel { (user: UserResponse) ->
        UserDetailViewModel(user)
    }
}