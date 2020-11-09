package io.github.sooakim.sfoide.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.github.sooakim.sfoide.utils.lifecycle.SingleData
import io.github.sooakim.sfoide.view.base.BaseViewModel
import io.github.sooakim.sfoide.view.base.ViewModelType
import io.github.sooakim.sfoide.view.detail.mapper.UserDetailMapper
import io.github.sooakim.sfoide.view.detail.model.UserDetailUiModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface UserDetailViewModelType: ViewModelType<UserDetailViewModelType.Input, UserDetailViewModelType.Output>{
    interface Input{
        fun onCellPhoneClick()

        fun onPhoneClick()

        fun onEmailClick()
    }

    interface Output{
        val user: LiveData<UserDetailUiModel>

        val startEmail: LiveData<SingleData<String>>

        val startPhone: LiveData<SingleData<String>>

        val startCellPhone: LiveData<SingleData<String>>
    }
}

class UserDetailViewModel(
    user: UserResponse
): BaseViewModel(), UserDetailViewModelType, UserDetailViewModelType.Input, UserDetailViewModelType.Output{
    override val input: UserDetailViewModelType.Input
        get() = this
    override val output: UserDetailViewModelType.Output
        get() = this

    private val _userSubject: BehaviorSubject<UserDetailUiModel> = BehaviorSubject.create()
    private val _user: MutableLiveData<UserDetailUiModel> = MutableLiveData()
    override val user: LiveData<UserDetailUiModel> = _user

    private val _onEmailClickSubject: PublishSubject<Unit> = PublishSubject.create()
    private val _startEmail: MutableLiveData<SingleData<String>> = MutableLiveData()
    override val startEmail: LiveData<SingleData<String>> = _startEmail

    private val _onPhoneClickSubject: PublishSubject<Unit> = PublishSubject.create()
    private val _startPhone: MutableLiveData<SingleData<String>> = MutableLiveData()
    override val startPhone: LiveData<SingleData<String>> = _startPhone

    private val _onCellPhoneClickSubject: PublishSubject<Unit> = PublishSubject.create()
    private val _startCellPhone: MutableLiveData<SingleData<String>> = MutableLiveData()
    override val startCellPhone: LiveData<SingleData<String>> = _startCellPhone

    init {
        val user = Observable.just(user)
                .replay(1)
                .autoConnect()

        user
                .map(UserDetailMapper::mapFrom)
                .subscribe(_userSubject::onNext)
                .let(compositeDisposable::add)

        _userSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_user::setValue)
                .let(compositeDisposable::add)

        _onEmailClickSubject
                .withLatestFrom(user, BiFunction{ _: Unit, user: UserResponse -> user })
                .map{ it.email }
                .map{ SingleData(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_startEmail::setValue)
                .let(compositeDisposable::add)

        _onPhoneClickSubject
                .withLatestFrom(user, BiFunction{ _: Unit, user: UserResponse -> user })
                .map{ it.phone.trim() }
                .map{ SingleData(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_startPhone::setValue)
                .let(compositeDisposable::add)

        _onCellPhoneClickSubject
                .withLatestFrom(user, BiFunction{ _: Unit, user: UserResponse -> user })
                .map{ it.cell.trim() }
                .map{ SingleData(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_startCellPhone::setValue)
                .let(compositeDisposable::add)
    }

    override fun onCellPhoneClick() {
        _onCellPhoneClickSubject.onNext(Unit)
    }

    override fun onPhoneClick() {
        _onPhoneClickSubject.onNext(Unit)
    }

    override fun onEmailClick() {
        _onEmailClickSubject.onNext(Unit)
    }
}