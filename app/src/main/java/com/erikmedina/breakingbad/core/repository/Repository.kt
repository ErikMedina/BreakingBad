package com.erikmedina.breakingbad.core.repository

import com.erikmedina.breakingbad.core.data.Character
import com.erikmedina.breakingbad.core.data.remote.network.ApiRest
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(private val apiRest: ApiRest) {

    fun getCharacters(): Single<List<Character>> {
        return apiRest.getCharacters()
            .map { response ->
                val characters = mutableListOf<Character>()
                for (characterEntity in response) {
                    characters.add(characterEntity.mapToCharacter())
                }
                characters
            }
    }
}
