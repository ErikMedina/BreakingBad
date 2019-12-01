package com.erikmedina.breakingbad.feature.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikmedina.breakingbad.core.result.Error
import com.erikmedina.breakingbad.core.result.Result
import com.erikmedina.breakingbad.core.result.Status
import com.erikmedina.breakingbad.core.usecase.GetCharactersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    val result = MutableLiveData<Result>()

    private val disposables = CompositeDisposable()

    fun getCharacters() {
        disposables.add(getCharactersUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { result.value = Result(status = Status.LOADING) }
            .subscribe(
                { characters ->
                    result.value = Result(status = Status.SUCCESS, data = characters)
                },
                {
                    result.value = Result(
                        status = Status.ERROR,
                        error = Error(Error.Type.GENERAL_ERROR)
                    )
                }
            )
        )
    }
}
