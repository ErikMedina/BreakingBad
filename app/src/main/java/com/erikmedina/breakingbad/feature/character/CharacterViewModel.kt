package com.erikmedina.breakingbad.feature.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erikmedina.breakingbad.core.data.Character
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

    var character: Character? = null

    val result = MutableLiveData<Result>()

    private var characters: List<Character> = emptyList()
    private var charactersFiltered: List<Character> = emptyList()
    private val disposables = CompositeDisposable()
    private var isFirstTime = true

    fun getCharacters() {
        if (isFirstTime) { // avoid new calls when the device is rotated
            isFirstTime = false
            disposables.add(getCharactersUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { result.value = Result(status = Status.LOADING) }
                .subscribe(
                    { characters ->
                        this.characters = characters
                        result.value = Result(status = Status.SUCCESS, data = this.characters)
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

    fun filterCharacterByName(query: String?) {
        charactersFiltered = characters.filter { it.name.contains(query!!, true) }
        result.value = Result(status = Status.SUCCESS, data = charactersFiltered)
    }

    fun filterCharactersBySeason(season: String?) {
        try {
            val seasonInt = season?.toInt()
            charactersFiltered = characters.filter { it.appearance.contains(seasonInt) }
            result.value = Result(status = Status.SUCCESS, data = charactersFiltered)
        } catch (nfe: NumberFormatException) {
            result.value = Result(status = Status.SUCCESS, data = characters)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
