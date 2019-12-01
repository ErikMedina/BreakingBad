package com.erikmedina.breakingbad.feature.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.erikmedina.breakingbad.R
import com.erikmedina.breakingbad.core.base.BaseFragment
import com.erikmedina.breakingbad.core.image.ImageLoader
import kotlinx.android.synthetic.main.fragment_character_detail.*
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment() {

    @Inject
    lateinit var fragmentActivity: FragmentActivity
    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresentationComponent().inject(this)

        // Shared ViewModel between Activity and Fragments
        viewModel = ViewModelProviders.of(fragmentActivity).get(CharacterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processUser()
    }

    private fun processUser() {
        viewModel.character?.run {
            imageLoader.loadImage(ivProfile, img)
            tvName.text = name
            tvOccupation.text = occupation.toString()
            tvStatus.text = status
            tvNickname.text = nickname
            tvSeason.text = appearance.toString()
        }
    }
}
