package com.erikmedina.breakingbad.core.usecase

import com.erikmedina.breakingbad.core.data.Character
import com.erikmedina.breakingbad.core.repository.Repository
import io.reactivex.Single
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: Repository) {

    fun execute(): Single<List<Character>> {
        return repository.getCharacters()
    }
}
