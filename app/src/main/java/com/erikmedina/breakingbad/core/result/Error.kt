package com.erikmedina.breakingbad.core.result

data class Error(var type: Type) {

    var message: Int = -1

    enum class Type {
        GENERAL_ERROR,
        NO_CHARACTERS
    }
}
