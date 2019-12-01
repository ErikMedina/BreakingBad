package com.erikmedina.breakingbad.core.data

data class Character(
    var charId: Long = 0,
    var img: String,
    var name: String,
    var occupation: List<String>,
    var status: String,
    var nickname: String,
    var appearance: List<Int>
)
