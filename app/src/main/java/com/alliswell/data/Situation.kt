package com.alliswell.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Situation (
    @PrimaryKey(autoGenerate = true) val situationId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?
) {
    constructor(title: String?, description: String?) : this(0, title, description)
}
