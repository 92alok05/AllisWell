package com.alliswell.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["patientId", "situationId"])
data class PatientSituation(
    @ColumnInfo(name = "patientId") val patientId: Int,
    @ColumnInfo(name = "situationId") val situationId: Int
)
