package com.alliswell.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Parameter(
    @PrimaryKey(autoGenerate = true) val parameterId: Int,
    @ColumnInfo(name = "name") val name: String?
) {
    constructor(name: String?): this(0, name)
}