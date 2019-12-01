package com.erikmedina.breakingbad.feature.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.erikmedina.breakingbad.R
import com.erikmedina.breakingbad.core.base.BaseFragment
import com.erikmedina.breakingbad.core.data.Character
import com.erikmedina.breakingbad.core.navigation.Navigator
import com.erikmedina.breakingbad.core.result.Error
import com.erikmedina.breakingbad.core.result.Result
import com.erikmedina.breakingbad.core.result.Status
import com.erikmedina.breakingbad.feature.character.adapter.CharacterAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_character_list.*
import javax.inject.Inject

class CharacterListFragment : BaseFragment() {

    @Inject
    lateinit var fragmentActivity: FragmentActivity
    @Inject
    lateinit var adapter: CharacterAdapter
    @Inject
    lateinit var navigator: Navigator

    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresentationComponent().inject(this)

        // Shared ViewModel between Activity and Fragments
        viewModel = ViewModelProviders.of(fragmentActivity).get(CharacterViewModel::class.java)
        viewModel.result.observe(this,
            Observer<Result> { result -> processResponse(result) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(fragmentActivity)
//        adapter.characterListener = {
//            viewModel.user = it
//            navigator.startPostDetailFragment()
//        }

        viewModel.getCharacters()
    }

    private fun processResponse(result: Result) {
        when (result.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(result.data)

            Status.ERROR -> renderErrorState(result.error)
        }
    }

    private fun renderLoadingState() {
        progressBar.visibility = View.VISIBLE
    }

    private fun renderDataState(characters: List<Character>) {
        progressBar.visibility = View.GONE
        adapter.setCharacters(characters)
    }

    private fun renderErrorState(error: Error?) {
        when (error?.type) {
            Error.Type.GENERAL_ERROR -> error.message =
                R.string.error_general
            Error.Type.NO_CHARACTERS -> error.message = R.string.error_no_characters
        }
        Log.e(TAG, error.toString())
        progressBar.visibility = View.GONE
        Snackbar.make(requireView(), error!!.message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "CharacterListFragment"
    }

}
