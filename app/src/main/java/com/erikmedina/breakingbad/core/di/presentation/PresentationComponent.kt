package com.erikmedina.breakingbad.core.di.presentation

import com.erikmedina.breakingbad.feature.character.CharacterDetailFragment
import com.erikmedina.breakingbad.feature.character.CharacterListFragment
import com.erikmedina.breakingbad.feature.character.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(characterListFragment: CharacterListFragment)

    fun inject(characterDetailFragment: CharacterDetailFragment)
}
