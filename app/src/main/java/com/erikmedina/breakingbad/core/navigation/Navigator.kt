package com.erikmedina.breakingbad.core.navigation

import androidx.fragment.app.FragmentActivity
import com.erikmedina.breakingbad.R
import com.erikmedina.breakingbad.feature.character.CharacterDetailFragment
import javax.inject.Inject

class Navigator @Inject constructor(private val fragmentActivity: FragmentActivity) {

    fun startPostDetailFragment() {
        // Create fragment and give it an argument specifying the article it should show
        val postDetailFragment = CharacterDetailFragment()
        val transaction = fragmentActivity.supportFragmentManager.beginTransaction().apply {
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            replace(R.id.fragment_container, postDetailFragment)
            addToBackStack(null)
        }
        transaction.commit()
    }
}
