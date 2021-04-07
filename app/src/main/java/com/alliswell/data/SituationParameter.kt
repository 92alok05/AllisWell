package com.alliswell.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["situationId", "parameterId"])
data class SituationParameter(
        @ColumnInfo(name = "situationId") val situationId: Int,
        @ColumnInfo(name = "parameterId") val parameterId: Int
)

