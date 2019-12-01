package com.erikmedina.breakingbad.core.result

import com.erikmedina.breakingbad.core.data.Character

class Result(
    val status: Status,
    val data: List<Character> = emptyList(),
    val error: Error? = null
)
