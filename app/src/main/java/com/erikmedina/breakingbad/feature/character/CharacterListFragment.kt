package com.erikmedina.breakingbad.feature.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

class CharacterListFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

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
        adapter.characterListener = {
            viewModel.character = it
            navigator.startCharacterDetailFragment()
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            fragmentActivity,
            R.array.seasons_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = this
        }

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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d(TAG, "[onItemSelected] position $position selected")
        val item = spinner.getItemAtPosition(position)
        viewModel.filterCharactersBySeason(item.toString())
    }

    companion object {
        private const val TAG = "CharacterListFragment"
    }

}
