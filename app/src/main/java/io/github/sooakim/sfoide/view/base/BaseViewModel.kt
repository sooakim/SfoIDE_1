package io.github.sooakim.sfoide.view.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(){
    protected val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

