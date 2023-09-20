package com.merveoz.sqliteexample.data.model

import com.merveoz.sqliteexample.R

enum class Priority( val priorityColorId: Int){
    HIGH(R.color.highPriorityColor),
    MEDIUM(R.color.mediumPriorityColor),
    LOW(R.color.lowPriorityColor);
}