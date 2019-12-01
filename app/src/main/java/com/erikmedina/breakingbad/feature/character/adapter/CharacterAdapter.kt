package com.erikmedina.breakingbad.feature.character.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erikmedina.breakingbad.R
import com.erikmedina.breakingbad.core.data.Character
import com.erikmedina.breakingbad.core.image.ImageLoader
import javax.inject.Inject

class CharacterAdapter @Inject constructor(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var characters = emptyList<Character>()

    internal var characterListener: (Character) -> Unit = { _ -> }

    override fun getItemCount() = characters.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun setCharacters(characters: List<Character>) {
        this.characters = characters
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var profile: ImageView = itemView.findViewById(R.id.ivProfile)
        var name: TextView = itemView.findViewById(R.id.tvName)
        var nickname: TextView = itemView.findViewById(R.id.tvNickname)

        fun bind(character: Character) {
            itemView.setOnClickListener {
                characterListener(character)
            }
            imageLoader.loadImage(profile, character.img)
            name.text = character.name
            nickname.text = character.nickname
        }
    }
}
