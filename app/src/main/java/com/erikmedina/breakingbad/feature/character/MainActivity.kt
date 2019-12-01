package com.erikmedina.breakingbad.feature.character

import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import com.erikmedina.breakingbad.R
import com.erikmedina.breakingbad.core.base.BaseActivity
import com.erikmedina.breakingbad.core.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Clients (in this case MainActivity) use Dagger component as injector to inject
        // services (dependencies) into themselves
        getPresentationComponent().inject(this)
        super.onCreate(savedInstanceState)
        // The ViewModel is initialised in the parent (activity) but will be shared by the children
        // as well. That's one of the beauty of the ViewModel, share data among an activity and its
        // children (fragments)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CharacterViewModel::class.java)
        setContentView(R.layout.activity_main)

        startCharacterListFragment(savedInstanceState)
    }

    private fun startCharacterListFragment(savedInstanceState: Bundle?) {
        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return
            }
            val characterListFragment = CharacterListFragment()

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            characterListFragment.arguments = intent.extras

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, characterListFragment).commit()
        }
    }
}
