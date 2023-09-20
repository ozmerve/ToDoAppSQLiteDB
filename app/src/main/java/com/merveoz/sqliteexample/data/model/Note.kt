package com.merveoz.sqliteexample.data.model

data class Note(
    val id: Int = 0,
    val title: String,
    val description: String,
    val priorityColorId: Int,
    var isChecked: Boolean = false
)
