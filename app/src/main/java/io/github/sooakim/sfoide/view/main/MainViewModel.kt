package io.github.sooakim.sfoide.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.sooakim.sfoide.remote.api.UserApi
import io.github.sooakim.sfoide.remote.resopnse.UserResponse
import io.github.sooakim.sfoide.utils.lifecycle.SingleData
import io.github.sooakim.sfoide.view.base.BaseViewModel
import io.github.sooakim.sfoide.view.base.ViewModelType
import io.github.sooakim.sfoide.view.main.mapper.UserListMapper
import io.github.sooakim.sfoide.view.main.model.UserUiModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.*

interface MainViewModelType: ViewModelType<MainViewModelType.Input, MainViewModelType.Output>{
    interface Input {
        fun onRefresh()

        fun onLoadMore()

        fun onScrollToTop()

        fun onMaleClick()

        fun onFemaleClick()

        fun onGenderFilterClick()
    }

    interface Output {
        val isRefreshing: LiveData<Boolean>

        val users: LiveData<List<UserUiModel>>

        val scrollToTop: LiveData<SingleData<Unit>>

        val startDetail: LiveData<SingleData<UserResponse>>

        val startGenderFilter: LiveData<SingleData<Unit>>

        val gender: LiveData<String>
    }
}

class MainViewModel(
        private val userApi: UserApi
) : BaseViewModel(), MainViewModelType, MainViewModelType.Input, MainViewModelType.Output {
    override val input: MainViewModelType.Input
        get() = this
    override val output: MainViewModelType.Output
        get() = this

    private val _isRefreshingSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    override val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _isPagingSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    private val _onRefreshSubject: PublishSubject<Unit> = PublishSubject.create()

    private val _onLoadMoreSubject: PublishSubject<Unit> = PublishSubject.create()

    private val _usersSubject: BehaviorSubject<List<UserUiModel>> = BehaviorSubject.createDefault(emptyList())
    private val _users: MutableLiveData<List<UserUiModel>> = MutableLiveData()
    override val users: LiveData<List<UserUiModel>> = _users

    private val _pageSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    private val _onScrollToTopSubject: PublishSubject<Unit> = PublishSubject.create()

    private val _scrollToTop: MutableLiveData<SingleData<Unit>> = MutableLiveData()
    override val scrollToTop: LiveData<SingleData<Unit>> = _scrollToTop

    private val _genderSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("male")
    private val _gender: MutableLiveData<String> = MutableLiveData()
    override val gender: LiveData<String> = _gender

    private val _onUserClick: PublishSubject<UserResponse> = PublishSubject.create()
    private val _startDetail: MutableLiveData<SingleData<UserResponse>> = MutableLiveData()
    override val startDetail: LiveData<SingleData<UserResponse>> = _startDetail

    private val _userListMapper: UserListMapper = UserListMapper(onClick = _onUserClick)

    private val _onGenderFilterClick: PublishSubject<Unit> = PublishSubject.create()

    private val _startGenderFilter: MutableLiveData<SingleData<Unit>> = MutableLiveData()
    override val startGenderFilter: LiveData<SingleData<Unit>> = _startGenderFilter

    init {
        val seedGenerator = _onRefreshSubject
                .startWith(Unit)
                .map { UUID.randomUUID().toString() }
                .share()

        val seedWithGender = Observable.combineLatest(
                seedGenerator,
                _genderSubject,
                BiFunction{ seed: String, gender: String -> Pair(seed, gender) }
        ).share().replay(1).autoConnect()

        val refreshing = seedWithGender
                .doOnNext { _isRefreshingSubject.onNext(true) }
                .switchMapSingle { (seed, gender) ->
                    userApi.getUsers(page = 0, results = RESULTS_COUNT, seed = seed, gender = gender)
                            .subscribeOn(Schedulers.io())
                }
                .materialize()
                .doOnNext { _isRefreshingSubject.onNext(false) }
                .share()

        val seedWithPageWithGender = Observable.combineLatest(
                seedWithGender,
                _pageSubject,
                BiFunction { seedWithGender: Pair<String, String>, page: Int -> Pair(seedWithGender, page) }
        ).share()

        val paging = _onLoadMoreSubject
                .filter { _isPagingSubject.value != true }
                .doOnNext { _isPagingSubject.onNext(true) }
                .withLatestFrom(seedWithPageWithGender, BiFunction{ _: Unit, seedWithPageWithGender: Pair<Pair<String, String>, Int> -> seedWithPageWithGender })
                .switchMapSingle { (seedWithGender, page) ->
                    val (seed, gender) = seedWithGender
                    userApi.getUsers(page = page, results = RESULTS_COUNT, seed = seed, gender = gender)
                            .subscribeOn(Schedulers.io())
                }
                .materialize()
                .doOnNext { _isPagingSubject.onNext(false) }
                .share()

        refreshing
                .filter { it.isOnNext }
                .map { it.value }
                .map { it.info.page }
                .subscribe(_pageSubject::onNext)
                .let(compositeDisposable::add)

        refreshing
                .filter { it.isOnNext }
                .map { it.value }
                .map { it.results }
                .map(_userListMapper::mapFrom)
                .subscribe(_usersSubject::onNext)
                .let(compositeDisposable::add)

        paging
                .filter { it.isOnNext }
                .map { it.value }
                .map { it.info.page + 1 }
                .subscribe(_pageSubject::onNext)
                .let(compositeDisposable::add)

        paging
                .filter { it.isOnNext }
                .map { it.value }
                .map { it.results }
                .map(_userListMapper::mapFrom)
                .subscribe { _usersSubject.onNext(_usersSubject.value!! + it) }
                .let(compositeDisposable::add)

        _usersSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_users::setValue)
                .let(compositeDisposable::add)

        _isRefreshingSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_isRefreshing::setValue)
                .let(compositeDisposable::add)

        _onScrollToTopSubject
                .map{ SingleData(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_scrollToTop::setValue)
                .let(compositeDisposable::add)

        _genderSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_gender::setValue)
                .let(compositeDisposable::add)

        _onUserClick
                .map{ SingleData(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_startDetail::setValue)
                .let(compositeDisposable::add)

        _onGenderFilterClick
                .map{ SingleData(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(_startGenderFilter::setValue)
                .let(compositeDisposable::add)
    }

    override fun onRefresh() {
        _onRefreshSubject.onNext(Unit)
    }

    override fun onLoadMore() {
        _onLoadMoreSubject.onNext(Unit)
    }

    override fun onScrollToTop() {
        _onScrollToTopSubject.onNext(Unit)
    }

    override fun onMaleClick() {
        _genderSubject.onNext("male")
    }

    override fun onFemaleClick() {
        _genderSubject.onNext("female")
    }

    override fun onGenderFilterClick() {
        _onGenderFilterClick.onNext(Unit)
    }

    companion object {
        private const val RESULTS_COUNT = 20
    }
}
