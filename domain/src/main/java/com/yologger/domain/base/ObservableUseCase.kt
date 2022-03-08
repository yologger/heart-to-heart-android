package com.yologger.domain.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class ObservableUseCase<Params, Type>: BaseUseCase {

    protected abstract fun call(params: Params? = null): Observable<Type>

    fun execute(params: Params?): Observable<Type> {
        return this.call(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}