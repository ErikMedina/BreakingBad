package com.erikmedina.breakingbad.core.viewmodel

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erikmedina.breakingbad.feature.character.CharacterViewModel
import javax.inject.Inject

/**
 * This factory is in charge of the injection of ViewModels in runtime.
 */
class ViewModelFactory @Inject constructor(private val userViewModel: CharacterViewModel) :
    ViewModelProvider.Factory {

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        val viewModel: ViewModel
        if (modelClass == CharacterViewModel::class.java) {
            viewModel = userViewModel
        } else {
            throw RuntimeException("invalid view model class: $modelClass")
        }

        return viewModel as T
    }
}
