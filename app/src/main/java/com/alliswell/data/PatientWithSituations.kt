package com.alliswell.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PatientWithSituations (
    @Embedded val Patient: Patient,
    @Relation(
        parentColumn = "patientId",
        entityColumn = "situationId",
        associateBy = Junction(PatientSituation::class)
    )
    val situations: List<Situation>
)