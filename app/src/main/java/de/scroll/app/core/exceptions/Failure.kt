package de.scroll.app.core.exceptions

class Failure(
    override val message : String,
    val showToast : Boolean = false,
) : Exception()

