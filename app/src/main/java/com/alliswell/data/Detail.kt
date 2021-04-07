package com.alliswell.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Detail(
    @PrimaryKey(autoGenerate = true) val detailId: Int,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "parameterId") val parameterId: Int,
    @ColumnInfo(name = "parameterValue") val parameterValue: Float,
    @ColumnInfo(name = "patientId") val patientId: Int,
    @ColumnInfo(name = "situationId") val situationId: Int
) {
    constructor(date: Date, parameterId: Int, parameterValue: Float, patientId: Int, situationId: Int):
            this(0, date, parameterId, parameterValue, patientId, situationId )
}
