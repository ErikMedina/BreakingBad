package com.erikmedina.breakingbad.core.data.remote.model

import com.erikmedina.breakingbad.core.data.Character
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This entity is how the data come from the server. It's useful to decouple our application from
 * the server since the application won't work with this entity directly but with a business model
 * mapped from this entity.
 *
 * Also, this class sets default values to the fields which don't have a response from the server.
 */
data class CharacterEntity(

    @SerializedName("char_id")
    @Expose
    var charId: Long = 0,
    @SerializedName("name")
    @Expose
    var name: String = "n/a",
    @SerializedName("birthday")
    @Expose
    var birthday: String = "n/a",
    @SerializedName("occupation")
    @Expose
    var occupation: List<String> = emptyList(),
    @SerializedName("img")
    @Expose
    var img: String = "n/a",
    @SerializedName("status")
    @Expose
    var status: String = "n/a",
    @SerializedName("nickname")
    @Expose
    var nickname: String = "n/a",
    @SerializedName("appearance")
    @Expose
    var appearance: List<Long> = emptyList(),
    @SerializedName("portrayed")
    @Expose
    var portrayed: String = "n/a",
    @SerializedName("category")
    @Expose
    var category: String = "n/a",
    @SerializedName("better_call_saul_appearance")
    @Expose
    var betterCallSaulAppearance: List<Long> = emptyList()
) {

    fun mapToCharacter(): Character {
        return Character(charId, img, name, occupation, status, nickname, appearance)
    }
}
